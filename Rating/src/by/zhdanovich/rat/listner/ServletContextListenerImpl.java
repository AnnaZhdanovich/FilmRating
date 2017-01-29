package by.zhdanovich.rat.listner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import by.zhdanovich.rat.dao.pool.ConnectionPool;

/**
 * Class {@code ServletContextListenerImpl} interface
 * {@code ServletContextListener}. He is a listener. Used in Project to invoke
 * the initialization process pool at the beginning of the application process
 * and closing the connection pool when the application shuts down.
 * 
 * @see by.zhdanovich.rat.dao.pool.ConnectionPool
 * @author Anna
 *
 */

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
	
	private ConnectionPool pool;

	public ServletContextListenerImpl() {
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		pool.dispose();
	}

	public void contextInitialized(ServletContextEvent event) {
		pool = ConnectionPool.getInstance();
	}
}
