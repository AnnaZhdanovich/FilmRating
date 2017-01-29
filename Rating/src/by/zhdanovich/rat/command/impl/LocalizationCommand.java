package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.controller.Carrier;

/**
 * Class {@code LocalizationCommand} executes the user's request to change the
 * locale.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 * 
 */
public class LocalizationCommand implements ICommand {
	/**
	 * Changes locale of user.
	 * 
	 * Lays down in the session locale chosen by the user.
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
		carrier.put(CommandParameter.METHOD, CommandParameter.SEND_REDIRECT);

		String locale = null;

		HttpSession session = request.getSession();

		if (session == null) {
			return;
		}
		locale = request.getParameter(CommandParameter.LOCALE);

		if (locale != null && !locale.isEmpty()) {
			session.setAttribute(CommandParameter.LOCALE, locale);
		}
		return;
	}

}
