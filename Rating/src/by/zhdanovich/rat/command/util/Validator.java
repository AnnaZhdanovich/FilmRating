package by.zhdanovich.rat.command.util;

import javax.servlet.http.HttpSession;

/**
 * Class {@code Validator} check the correctness of the data from the
 * request.
 * 
 * @author Anna
 *
 */
public class Validator {
	/**
	 * Checks whether the user is in the system.
	 * 
	 * Checking for null is the user id in the session
	 * 
	 * @param session
	 * @return if the condition is true it is returned if not satisfied return
	 *         false
	 */
	public static boolean checkAuthorisation(HttpSession session) {
		if (session.getAttribute(CommandParameter.PARAM_NAME_ID) == null) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the array parameter to null or empty
	 * 
	 * @param i
	 * @return if the condition is true it is returned if not satisfied return
	 *         false
	 */

	public static boolean check(String[] i) {
		boolean result = false;
		if (i != null && i.length != 0) {
			result = true;
		}
		return result;
	}

	/**
	 * Checks whether empty or equal to null string
	 * 
	 * @param param
	 * @return if the condition is true it is returned if not satisfied return
	 *         false
	 */
	public static boolean check(String param) {
		boolean result = false;
		if (param != null && (!param.isEmpty())) {
			result = true;
		}
		return result;
	}

	/**
	 * Checks whether the password specified pattern
	 * 
	 * @param password
	 * @return if the condition is true it is returned if not satisfied return
	 *         false
	 */
	public static boolean checkPassword(String password) {
		boolean result = false;
		if (check(password) && password.matches(CommandParameter.REG_EXP_PASSWORD)) {
			result = true;
		}
		return result;
	}

	/**
	 * Checks whether the title specified pattern
	 * 
	 * @param password
	 * @return if the condition is true it is returned if not satisfied return
	 *         false
	 */
	public static boolean checkTitle(String title) {
		boolean result = false;
		if (check(title) && title.matches(CommandParameter.REG_EXP_TITLE)) {
			result = true;
		}
		return result;
	}

	/**
	 * Checks whether the login specified pattern
	 * 
	 * @param password
	 * @return if the condition is true it is returned if not satisfied return
	 *         false
	 */
	public static boolean checkLogin(String login) {
		boolean result = false;
		if (check(login) && login.matches(CommandParameter.REG_EXP_LOGIN)) {
			result = true;
		}
		return result;
	}

	/**
	 * Checks whether the name specified pattern
	 * 
	 * @param password
	 * @return if the condition is true it is returned if not satisfied return
	 *         false
	 */
	public static boolean checkName(String name) {
		boolean result = false;
		if (check(name) && name.matches(CommandParameter.REG_EXP_NAME)) {
			result = true;
		}
		return result;
	}

	/**
	 * Checks whether the email specified pattern
	 * 
	 * @param password
	 * @return if the condition is true it is returned if not satisfied return
	 *         false
	 */
	public static boolean checkEmail(String email) {
		boolean result = false;
		if (check(email) && email.matches(CommandParameter.REG_EXP_EMAIL)) {
			result = true;
		}
		return result;
	}

}
