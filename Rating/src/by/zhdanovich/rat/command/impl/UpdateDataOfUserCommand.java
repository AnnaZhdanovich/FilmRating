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
 * Class {@code UpdateDataOfFilmCommand} processes the request to update film
 * data in the database.
 *
 * It implements the {@code ICommand} interface and implements its only method.
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 *
 */
public class UpdateDataOfUserCommand implements ICommand {
	/**
	 * Update user data.
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
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {
		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);
		HttpSession session = request.getSession();
		session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
		try {
			if (Validator.checkAuthorisation(session)) {
				request.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
				return;
			}

			String login = request.getParameter(CommandParameter.PARAM_NAME_LOGIN);
			String password = request.getParameter(CommandParameter.PARAM_NAME_PASSWORD);
			String firstname = request.getParameter(CommandParameter.PARAM_NAME_FIRSTNAME);
			String email = request.getParameter(CommandParameter.PARAM_NAME_EMAIL);

			if (Validator.checkName(firstname) && Validator.checkPassword(password) && Validator.checkLogin(login)
					&& Validator.checkEmail(email)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IClientService clientService = sevice.getClientService();

				int user_id = (int) session.getAttribute(CommandParameter.PARAM_NAME_ID);

				boolean result = clientService.updateUserData(firstname, login, password, email, user_id);

				if (result) {
					request.setAttribute(CommandParameter.MESSAGE_UPDATE_USER, CommandParameter.MESSAGE);

				} else {
					request.setAttribute(CommandParameter.ERROR_UPDATE_MESSAGE, CommandParameter.MESSAGE);

				}
			} else {

				request.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);

			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);

		}
	}

}
