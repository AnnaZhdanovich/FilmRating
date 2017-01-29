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
 * Command {@code TakeDataOfUserCommand}  find data of user.
 * 
 * @author Anna
 *
 */
public class TakeDataOfUserCommand implements ICommand {
	/**
	 * Search all comments of some film.
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
			int idUser = (int) session.getAttribute(CommandParameter.PARAM_NAME_ID);

			ServiceFactory sevice = ServiceFactory.getInstance();
			IClientService client = sevice.getClientService();

			User user;

			user = client.change(idUser);

			if (user != null) {
				request.setAttribute(CommandParameter.USER, user);
				session.setAttribute(CommandParameter.TARGET, CommandParameter.USER_CHANGES);
			} else {
				request.setAttribute(CommandParameter.ERROR, CommandParameter.MESSAGE);
				session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}
	}
}
