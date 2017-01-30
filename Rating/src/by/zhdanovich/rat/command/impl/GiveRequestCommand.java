package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.util.Carrier;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.service.IClientService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code GiveRequestCommand} performs the request (command) to add a new
 * request of user.
 * 
 * @author Anna It implements the {@code ICommand} interface and implements its
 *         only method
 *         {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 */
public class GiveRequestCommand implements ICommand {
	/**
	 * Adding a request.
	 * 
	 * Check the user is logged in, and then checks all data from the request is
	 * correct, audited data is transmitted to the service level. The response
	 * is analyzed further in session falls message about the outcome of the
	 * implementation.
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

		HttpSession session = request.getSession();
		User user = null;

		String login = request.getParameter(CommandParameter.LOGIN);
		String password = request.getParameter(CommandParameter.PASSWORD);
		String text = request.getParameter(CommandParameter.TEXT);

		try {

			if (Validator.check(login) && Validator.check(password) && Validator.check(text)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IClientService clientService = sevice.getClientService();

				user = clientService.authorizeUser(login, password);

				if (user != null) {

					boolean result = false;

					result = clientService.giveRequest(user.getIdUser(), text);

					session.setAttribute(CommandParameter.TARGET, CommandParameter.GIVE_REQUEST);
					if (result) {
						session.setAttribute(CommandParameter.ADD_REQUEST, CommandParameter.MESSAGE);
					} else {
						session.setAttribute(CommandParameter.ERROR, CommandParameter.MESSAGE);
						session.setAttribute(CommandParameter.TARGET, CommandParameter.GIVE_REQUEST);
					}
				} else {
					session.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
					session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
				}
			} else {
				session.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
				session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
			}

		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}
	}
}
