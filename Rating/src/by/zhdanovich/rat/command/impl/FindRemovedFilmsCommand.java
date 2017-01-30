package by.zhdanovich.rat.command.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.util.Carrier;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.service.IAdminService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code FindRemovedFilmsCommand} class processes the request for all the
 * films that have been put into the archive.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException}
 * 
 * @author Anna
 *
 */
public class FindRemovedFilmsCommand implements ICommand {
	/**
	 * Returns the films that have been put into the archive.
	 * 
	 * At first user authorization check, and then checks all data from the
	 * request is correct, audited data is transmitted to the service level. The
	 * response is analyzed further in session falls message about the outcome
	 * of the implementation.
	 * 
	 * @param request
	 *            request of user
	 * @param carrier
	 *            carrier which in itself contains the information on the basis
	 *            of which the selected method of sending a response to client.
	 * @throws CommandException
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {

		HttpSession session = request.getSession();

		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);

		if (Validator.checkAuthorisation(session)) {
			request.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}

		String pageIn = request.getParameter(CommandParameter.PAGE);

		int page = CommandParameter.PAGE_DEFAULT;

		if (Validator.check(pageIn))
			page = Integer.parseInt(pageIn);
		try {

			ServiceFactory sevice = ServiceFactory.getInstance();
			IAdminService adminService = sevice.getAdminService();

			List<Film> films = new ArrayList<Film>();

			int noOfRecords = adminService.findRemoveFilm(films, (page - 1) * CommandParameter.RECORDS_PER_PAGE,
					CommandParameter.RECORDS_PER_PAGE);

			session.setAttribute(CommandParameter.TARGET, CommandParameter.REMOVED_FILM);

			if (noOfRecords != 0) {
				int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / CommandParameter.RECORDS_PER_PAGE);
				session.setAttribute(CommandParameter.NO_OF_PAGES, noOfPages);
				session.setAttribute(CommandParameter.CURRENT_PAGES, page);
				request.setAttribute(CommandParameter.FILMS, films);

			} else {
				request.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);
			}

		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}
	}
}
