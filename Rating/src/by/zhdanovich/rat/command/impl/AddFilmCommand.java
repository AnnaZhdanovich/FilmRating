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
import by.zhdanovich.rat.service.IAdminService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code AddFilmCommand} performs the request (command) to add a new film
 * to the database.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException}
 * 
 * @author Anna
 */

public class AddFilmCommand implements ICommand {
	/**
	 * Adding a new film.
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
	 * 
	 */

	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {
		carrier.put(CommandParameter.METHOD, CommandParameter.SEND_REDIRECT);

		HttpSession session = request.getSession();

		if (Validator.checkAuthorisation(session)) {
			request.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}

		String path = request.getServletContext().getRealPath(CommandParameter.FOLDER);
		String actors[] = request.getParameterValues(CommandParameter.ACTOR);
		String producers[] = request.getParameterValues(CommandParameter.PRODUCER);
		String title = request.getParameter(CommandParameter.TITLE);
		String country = request.getParameter(CommandParameter.COUNTRY);
		String description = request.getParameter(CommandParameter.DESCRIPTION);
		String year = request.getParameter(CommandParameter.YEAR);
		String[] genre = request.getParameterValues(CommandParameter.GENRE);
		Part filePart = null;

		try {
			if (request.getPart(CommandParameter.FILE) != null) {
				filePart = request.getPart(CommandParameter.FILE);
			}

			if (Validator.checkTitle(title) && Validator.check(actors) && Validator.check(producers)
					&& Validator.check(country) && Validator.check(description) && Validator.check(year)
					&& Validator.check(genre)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IAdminService adminService = sevice.getAdminService();

				boolean result = adminService.addFilm(title, country, actors, producers, year, genre, filePart, path,
						description);
				if (result) {
					session.setAttribute(CommandParameter.ADD_MESSAGE, CommandParameter.MESSAGE);
				} else {
					session.setAttribute(CommandParameter.ERROR_ADD, CommandParameter.MESSAGE);
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
