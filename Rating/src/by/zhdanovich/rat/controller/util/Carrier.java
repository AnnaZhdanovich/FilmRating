package by.zhdanovich.rat.controller.util;

import java.util.HashMap;
import java.util.Map;

/**
 * The class is a  utility class.
 * 
 * The class is a wrapper for a map which is stored and transmitted the
 * parameters that define the way to send the user to answer.
 * 
 * @author Anna
 *
 */

public class Carrier {

	private Map<String, String> map;
	
	public Carrier() {
		map = new HashMap<String, String>();
	}
/**
 * Adds values to map.
 * @param key
 * @param value
 */
	public void put(String key, String value) {
		map.put(key, value);
	}
/**
 * Takes value from map.
 * @param key
 * @return values from map
 */
	public String get(String key) {
		return map.get(key);
	}
}
