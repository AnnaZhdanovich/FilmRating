package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.controller.Carrier;

/**
 * Class {@code LogoutCommand} class is used to kill the session.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 * 
 */
public class LogoutCommand implements ICommand {
	/**
	 * Session dies, and the user logs out.
	 * 
	 * Falls into the session locale chosen by the user.
	 * 
	 * @param request
	 *            request of user
	 * @param carrier
	 *            object which in itself contains the information on the basis of
	 *            which will be selected method of sending a response to client.
	 * @throws CommandException
	 * 
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {

		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);

		HttpSession session = request.getSession();

		session.invalidate();

		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);

	}

}