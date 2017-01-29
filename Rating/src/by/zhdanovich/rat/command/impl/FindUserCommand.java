package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.Carrier;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.service.IClientService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code FindUserCommand} executes a query a user searches on its ID
 * 
 * It implements the {@code ICommand} interface and implements its only method.
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 *
 */
public class FindUserCommand implements ICommand {
	/**
	 * Find user by ID.
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

		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);
		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);

		HttpSession session = request.getSession();

		if (Validator.checkAuthorisation(session)) {
			request.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}
		try {
			String idUser = request.getParameter(CommandParameter.UID_USER);

			if (Validator.check(idUser)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IClientService clientService = sevice.getClientService();

				User user;

				user = clientService.findUser(Integer.parseInt(idUser));

				if (user != null) {
					request.setAttribute(CommandParameter.USER, user);
					session.setAttribute(CommandParameter.TARGET, CommandParameter.PERSONAL);

				} else {
					request.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);
					session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
				}

			} else {
				request.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
				session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);

			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}
	}

}
