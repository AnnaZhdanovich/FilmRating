package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.util.Carrier;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.service.IAdminService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code FindUserByLoginCommand} executes the query on the user's search
 * for his login.
 * 
 * It implements the {@code ICommand} interface and implements its only method.
 * {@code execute(HttpServletRequest request, Map<String, String> map) throws CommandException }
 * 
 * @author Anna
 *
 */
public class FindUserByLoginCommand implements ICommand {
	/**
	 * Find user by login.
	 * 
	 * User authorization check, and then checks all data from the request is
	 * correct, audited data is transmitted to the service level. The response
	 * is analyzed further in session falls message about the outcome of the
	 * implementation.
	 * 
	 * @param request
	 *            request of user
	 * @param map
	 *            map which in itself contains the information on the basis of
	 *            which the selected method of sending a response to client.
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
			String login = request.getParameter(CommandParameter.PARAM_NAME_LOGIN);

			if (Validator.checkLogin(login)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IAdminService adminService = sevice.getAdminService();

				User user;
				user = adminService.findUserByLogin(login);
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
