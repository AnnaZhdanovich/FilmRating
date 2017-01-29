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
 * Class {@code UpdateDataOfFilmCommand} processes the request to update film
 * data in the database.
 *
 * It implements the {@code ICommand} interface and implements its only method.
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 *
 */
public class UpdateDataOfFilmCommand implements ICommand {
	/**
	 * Update film data in the database.
	 * 
	 * * Check the user is logged in, and then checks all data from the request
	 * is correct, audited data is transmitted to the service level. The
	 * response is analyzed, further in session falls message about the outcome
	 * of the implementation.
	 * 
	 * @param request
	 *            request of user
	 * @param carrier
	 *            object which in itself contains the information on the basis
	 *            of which will be selected method of sending a response to
	 *            client.
	 * @throws CommandException
	 */
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {

		carrier.put(CommandParameter.METHOD, CommandParameter.SEND_REDIRECT);
		HttpSession session = request.getSession();

		if (Validator.checkAuthorisation(session)) {
			session.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}

		try {
			String title = request.getParameter(CommandParameter.TITLE);
			String country = request.getParameter(CommandParameter.COUNTRY);
			String year = request.getParameter(CommandParameter.YEAR);
			String idFilm = request.getParameter(CommandParameter.FILM_UID);

			if (Validator.check(idFilm) && Validator.checkTitle(title) && Validator.check(country)
					&& Validator.check(year)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IAdminService adminService = sevice.getAdminService();

				boolean result = adminService.updateDataFilm(title, country, year, Integer.parseInt(idFilm));
				if (result) {
					session.setAttribute(CommandParameter.MESSAGE_UPDATE_USER, CommandParameter.MESSAGE);
				} else {
					session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
					session.setAttribute(CommandParameter.ERROR_UPDATE_MESSAGE, CommandParameter.MESSAGE);
					carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
				}
			} else {
				session.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
				carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);

		}
	}
}
