package by.zhdanovich.rat.dao.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class {@code ConnectionPool} is used to create a queue of connections to the
 * database.
 * 
 * @author Anna
 *
 */

public class ConnectionPool {
	
	private static Logger log = LogManager.getLogger(ConnectionPool.class);
	private static final ConnectionPool INSTANCE = new ConnectionPool();
	private static final int DEFAULT_POOL_SIZE = 5;
	private BlockingQueue<ProxyConnection> connectionQueue;
	private BlockingQueue<ProxyConnection> givenAwayConQueue;
	private String url;
	private String user;
	private String password;
	private int poolSize;
	private String driverName;

	/**
	 * 
	 * @return reference on the single object of ConnectionPool.
	 */

	public static ConnectionPool getInstance() {
		return INSTANCE;
	}

	private ConnectionPool() {
		DBResourceManager dbResourseManager = DBResourceManager.getInstance();
		this.driverName = dbResourseManager.getValue(DBParameter.DB_DRIVER);
		this.url = dbResourseManager.getValue(DBParameter.DB_URL);
		this.user = dbResourseManager.getValue(DBParameter.DB_USER);
		this.password = dbResourseManager.getValue(DBParameter.DB_PASSWORD);
		try {
			this.poolSize = Integer.parseInt(dbResourseManager.getValue(DBParameter.DB_POLL_SIZE));
		} catch (NumberFormatException e) {
			log.error("Wrong determining poolSize");
			poolSize = DEFAULT_POOL_SIZE;
		}
		try {
			initPoolData();
		} catch (ConnectionPoolException e) {
			log.fatal("Error creating connections", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Pool initialization. Create a connection amount equal to @{poolSize} and
	 * placed in a queue.
	 * 
	 * @throws ConnectionPoolException
	 */
	private void initPoolData() throws ConnectionPoolException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			throw new ConnectionPoolException("SQLException in ConnectionPool", e);
		}
		givenAwayConQueue = new ArrayBlockingQueue<ProxyConnection>(poolSize);
		connectionQueue = new ArrayBlockingQueue<ProxyConnection>(poolSize);

		for (int i = 0; i < poolSize; i++) {
			try {
				Connection con = DriverManager.getConnection(url, user, password);
				ProxyConnection connection = new ProxyConnection(con);
				connectionQueue.put(connection);
			} catch (SQLException | InterruptedException e) {
				throw new ConnectionPoolException("SQLException in ConnectionPool", e);
			}
		}

	}

	public void dispose() {
		closeConnectionsQueue(connectionQueue);
	}

	/**
	 * Closing a connections
	 * 
	 * @throws ConnectionPoolException
	 */
	private void closeConnectionsQueue(BlockingQueue<ProxyConnection> queue) {
		ProxyConnection connection;
		try {
			for (int i = 0; i < poolSize; i++) {
				connection = queue.take();
				connection.realClose();
			}
		} catch (SQLException | InterruptedException e) {
			log.error("Error closing connections", e);
		}
	}

	/**
	 * 
	 * @return reference on object of ProxyConnection
	 * @throws ConnectionPoolException
	 */
	public ProxyConnection takeConnection() throws ConnectionPoolException {

		ProxyConnection connection = null;

		try {
			connection = connectionQueue.take();
			givenAwayConQueue.put(connection);

		} catch (InterruptedException e) {
			throw new ConnectionPoolException("Error connecting to the data source.", e);
		}

		return connection;
	}

	/**
	 * Returns the connection back to the pool.
	 * 
	 * @param connection
	 * @throws ConnectionPoolException
	 */
	public void releaseConnection(ProxyConnection connection) throws ConnectionPoolException {

		if (connection == null) {
			throw new ConnectionPoolException("null SQLException in ConnectionPool");
		}
		try {
			if (!connection.getAutoCommit()) {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new ConnectionPoolException("SQLException in ConnectionPool", e);
		}
		givenAwayConQueue.remove(connection);
		try {
			connectionQueue.put(connection);
		} catch (InterruptedException e) {
			log.error("Error releasing connections", e);
		}
	}
}
