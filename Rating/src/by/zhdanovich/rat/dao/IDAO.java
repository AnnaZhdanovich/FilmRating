package by.zhdanovich.rat.dao;

import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.zhdanovich.rat.dao.exception.DAOException;
import by.zhdanovich.rat.dao.pool.ConnectionPool;
import by.zhdanovich.rat.dao.pool.ConnectionPoolException;
import by.zhdanovich.rat.dao.pool.ProxyConnection;

public interface IDAO {
	static Logger log = LogManager.getLogger(IDAO.class);

	/**
	 * There is a closing object {@code Statement} which leads the closing
	 * {@cod}ResultSet}.
	 * 
	 * @param st
	 */
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				log.error("Wrong of closing statement, it can not be closed", e);
			}
		}
	}

	/**
	 * There is a closing object {@code ProxyConnection}
	 * {@see by.zhdanovich.rat.dao.pool.ConnectionPool}
	 * 
	 * @param con
	 * @throws DAOException 
	 */
	public static void closeConnection(ProxyConnection con) throws DAOException {
		if (con != null) {
			try {
				ConnectionPool.getInstance().releaseConnection(con);
			} catch (ConnectionPoolException e) {				
				throw new DAOException("Wrong of closing connection, it can not be closed", e);
			}
		}
	}
}
