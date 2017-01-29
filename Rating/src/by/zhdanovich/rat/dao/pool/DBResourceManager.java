package by.zhdanovich.rat.dao.pool;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class that extracts of the properties-file the necessary information.
 * 
 * @author Anna
 *
 */
public class DBResourceManager {
	private static Logger log = LogManager.getLogger(DBResourceManager.class);
	private static final String RESOURCES = "resources.db";
	private static final DBResourceManager instance = new DBResourceManager();
	private ResourceBundle bundle;

	private DBResourceManager() {
		try {
			bundle = ResourceBundle.getBundle(RESOURCES);
		} catch (MissingResourceException e) {
			log.fatal("Failed to get the resource file", e);
			throw new RuntimeException("Wrong, application can not work");
		}
	}

	/**
	 * Returns the value of the property by a key file.
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return bundle.getString(key);
	}

	/**
	 * 
	 * @return reference on object of @(code DBResourceManager)
	 */
	public static DBResourceManager getInstance() {
		return instance;
	}
}
