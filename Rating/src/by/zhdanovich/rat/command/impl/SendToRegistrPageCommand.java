package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.controller.util.Carrier;

/**
 * Class {@SendToRegistrPageCommand} is used to move the user to a registration
 * page.
 * 
 * @author Anna
 *
 */
public class SendToRegistrPageCommand implements ICommand {
	/**
	 * Move the user to a registration
     * page.
	 * @param request
	 *            request of user
	 * @param carrier
	 *            object which in itself contains the information on the basis of
	 *            which will be selected method of sending a response to client.
	 * @throws CommandException
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {
		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);

		HttpSession session = request.getSession();
		session.setAttribute(CommandParameter.TARGET, CommandParameter.REGISTRATION);

	}

}
