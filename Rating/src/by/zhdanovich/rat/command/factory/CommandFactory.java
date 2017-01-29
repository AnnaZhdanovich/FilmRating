package by.zhdanovich.rat.command.factory;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;

/**
 * Class {@code CommandHelper} is used to determine the object of the command from the
 * presented  data with the request.
 * 
 * @author Anna
 *
 */
public class CommandFactory {

	private static Logger log = LogManager.getLogger(CommandFactory.class);

	/**
	 * According to data obtained from the query to retrieve the object of
	 * command. 
	 * 
	 * {@link by.zhdanovich.rat.command.CommandType}
	 * @param request
	 * @return object some command.
	 */

	public ICommand defineCommand(HttpServletRequest request) {
		ICommand current = null;
		String action = request.getParameter(CommandParameter.COMMAND);
		if (!Validator.check(action)) {
			return current;
		}
		try {
			CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
			current = currentEnum.getCurrentCommand();
		} catch (IllegalArgumentException e) {
			log.error("Error command selection", e);
		}
		return current;
	}
}
