package by.zhdanovich.rat.command.impl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
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
 * Class {@code RegistrationCommand} performs user registration.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 */
public class RegistrationCommand implements ICommand {
	/**
	 * Registration of user and add his data to database.
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

		carrier.put(CommandParameter.METHOD, CommandParameter.SEND_REDIRECT);
		User user = null;
		Part filePart = null;
		HttpSession session = request.getSession(true);

		try {
			final String path = request.getServletContext().getRealPath(CommandParameter.FOLDER);
			String login = request.getParameter(CommandParameter.PARAM_NAME_LOGIN);
			String password = request.getParameter(CommandParameter.PARAM_NAME_PASSWORD);
			String firstname = request.getParameter(CommandParameter.PARAM_NAME_FIRSTNAME);
			String email = request.getParameter(CommandParameter.PARAM_NAME_EMAIL);

			if (request.getPart(CommandParameter.FILE) != null) {
				filePart = request.getPart(CommandParameter.FILE);
			}

			if (Validator.checkName(firstname) && Validator.checkPassword(password) && Validator.checkLogin(login)
					&& Validator.checkEmail(email)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IClientService clientService = sevice.getClientService();

				if (clientService.checkLogin(login)) {
					session.setAttribute(CommandParameter.ERROR_FREE_LOGIN, CommandParameter.MESSAGE);
					session.setAttribute(CommandParameter.TARGET, CommandParameter.REGISTRATION);
				} else {
					user = clientService.registrUser(filePart, path, firstname, login, password, email);
					if (user != null) {
						session.setAttribute(CommandParameter.PARAM_NAME_ID, user.getIdUser());
						session.setAttribute(CommandParameter.NAME, user.getFirstName());
						session.setAttribute(CommandParameter.ROLE, user.getRole());
					} else {
						session.setAttribute(CommandParameter.ERROR, CommandParameter.MESSAGE);
					}
				}
			} else {
				session.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
			}

		} catch (ServletException e) {
			throw new CommandException("Wrong of servlet when executing the command", e);
		} catch (IOException e) {
			throw new CommandException("Wrong I/O when executing the command", e);
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}
	}
}
