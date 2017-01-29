package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.Carrier;
import by.zhdanovich.rat.service.IClientService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code AddFilmCommand} allows you to remove the user from the database.
 * 
 * @author Anna
 */
public class DeleteUserCommand implements ICommand {
	/**
	 * Delete data of user.
	 * 
	 * User authorization check, and then checks all data from the request is
	 * correct, audited data is transmitted to the service level. The response
	 * is analyzed, further in session falls message about the outcome of the
	 * implementation.
	 * 
	 * @param request
	 *            request of user
	 * @param carrier
	 *            object which in itself contains the information on the basis of
	 *            which will be selected method of sending a response to client.
	 * @throws CommandException
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {

		HttpSession session = request.getSession();

		if (Validator.checkAuthorisation(session)) {
			request.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}

		try {
			
			int idUser = (int) session.getAttribute(CommandParameter.PARAM_NAME_ID);

			ServiceFactory factory = ServiceFactory.getInstance();
			IClientService client = factory.getClientService();

			boolean result = false;
			result = client.deleteUser(idUser);

			if (result) {
				session.invalidate();
				carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
				carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);

			} else {
				session.setAttribute(CommandParameter.ERROR, CommandParameter.MESSAGE);
				carrier.put(CommandParameter.PAGE, CommandParameter.SEND_REDIRECT);
			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);

		}
	}
}
