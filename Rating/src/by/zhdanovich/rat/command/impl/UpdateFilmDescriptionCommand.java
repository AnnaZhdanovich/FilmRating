package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.Carrier;
import by.zhdanovich.rat.service.IAdminService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;
/**
 * Class {@code UpdateFilmDescriptionCommand} processes the request to update film description
 * data in the database.
 *
 * It implements the {@code ICommand} interface and implements its only method.
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 *
 */
public class UpdateFilmDescriptionCommand implements ICommand {
	/**
	 * Update film description.
	 *
	 * Check the user is logged in, and then checks all data from the request is
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

		carrier.put(CommandParameter.METHOD, CommandParameter.SEND_REDIRECT);

		HttpSession session = request.getSession();
		session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
		if (Validator.checkAuthorisation(session)) {
			session.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}
		try {

			String description = request.getParameter(CommandParameter.DESCRIPTION);
			String idFilm = request.getParameter(CommandParameter.FILM_UID);
			if (Validator.check(idFilm) && Validator.check(description)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IAdminService adminService = sevice.getAdminService();

				boolean result = adminService.updateFilmDescription(description, Integer.parseInt(idFilm));

				if (result) {
					session.setAttribute(CommandParameter.MESSAGE_UPDATE_USER, CommandParameter.MESSAGE);
				} else {
					session.setAttribute(CommandParameter.ERROR_UPDATE_MESSAGE, CommandParameter.MESSAGE);
				}
			} else {
				session.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);

		}
	}
}
