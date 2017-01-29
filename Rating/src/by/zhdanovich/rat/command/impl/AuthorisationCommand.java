package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.Carrier;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.entity.User.Status;
import by.zhdanovich.rat.service.IClientService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code AuthorisationCommand} perform a request for user authorization
 * check
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 */
public class AuthorisationCommand implements ICommand {
	/**
	 * At first this method checks all data from the request is correct, audited
	 * data is transmitted to the service level. The response is analyzed,
	 * further in session falls message about the outcome of the implementation.
	 * 
	 * @param request
	 *            request of user
	 * @param carrier
	 *            object which in itself contains the information on the basis
	 *            of which will be selected method of sending a response to
	 *            client.
	 * @throws CommandException
	 */

	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {

		carrier.put(CommandParameter.METHOD, CommandParameter.SEND_REDIRECT);
		
		HttpSession session = request.getSession();

		User user = null;

		String login = request.getParameter(CommandParameter.PARAM_NAME_LOG);
		String password = request.getParameter(CommandParameter.PARAM_NAME_PASS);

		try {
			if (Validator.check(login) && Validator.check(password)) {
				ServiceFactory sevice = ServiceFactory.getInstance();
				IClientService clientService = sevice.getClientService();

				user = clientService.authorizeUser(login, password);

				if (user != null) {
					if (user.getStatus() != Status.BLOCK) {
						session.setAttribute(CommandParameter.PARAM_NAME_ID, user.getIdUser());
						session.setAttribute(CommandParameter.NAME, user.getFirstName());
						session.setAttribute(CommandParameter.ROLE, user.getRole());
						session.setAttribute(CommandParameter.IMAGE, user.getImage());
					} else {
						session.setAttribute(CommandParameter.ERROR_STATUS_MESSAGE, CommandParameter.MESSAGE);
					}
				} else {
					session.setAttribute(CommandParameter.ERROR_LOGIN_PASS_MESSAGE, CommandParameter.MESSAGE);
				}
			} else {
				session.setAttribute(CommandParameter.ERROR_INPUT_DATA, CommandParameter.MESSAGE);
			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}
		return;
	}
}
