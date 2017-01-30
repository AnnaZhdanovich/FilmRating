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
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code FindListOfFilmsCommand} performs the request (command) to find
 * all the films that match the search conditions.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 *
 */
public class FindFilmsListCommand implements ICommand {
	/**
	 * find list of films from database if they match the search conditions.
	 * 
	 * Gets the data necessary to search for the query and checks them for
	 * correctness, audited data is transmitted to the service level. The
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
	 * 
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {
		HttpSession session = request.getSession();

		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);
		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
		String actor = request.getParameter(CommandParameter.ACTOR1);
		String producer = request.getParameter(CommandParameter.PRODUCER1);
		String title = request.getParameter(CommandParameter.TITLE);
		String country = request.getParameter(CommandParameter.COUNTRY);
		String year = request.getParameter(CommandParameter.YEAR);
		String genre = request.getParameter(CommandParameter.GENRE1);
		String goal = request.getParameter(CommandParameter.GOAL);
		String pageIn = request.getParameter(CommandParameter.PAGE);

		try {
			int page = CommandParameter.PAGE_DEFAULT;

			if (Validator.check(pageIn)) {
				page = Integer.parseInt(pageIn);
			}
			if (Validator.check(goal)) {
				List<Film> list = new ArrayList<Film>();

				ServiceFactory sevice = ServiceFactory.getInstance();
				ICommonService commonService = sevice.getCommonService();

				int noOfRecords = commonService.findListOfFilms(list, title, country, actor, producer, year, genre,
						(page - 1) * CommandParameter.RECORDS_PER_PAGE, CommandParameter.RECORDS_PER_PAGE);
				if (noOfRecords != 0) {
					int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / CommandParameter.RECORDS_PER_PAGE);
					session.setAttribute(CommandParameter.NO_OF_PAGES, noOfPages);
					session.setAttribute(CommandParameter.CURRENT_PAGES, page);
					request.setAttribute(CommandParameter.FILMS, list);
					session.setAttribute(CommandParameter.TITLE, title);
					session.setAttribute(CommandParameter.COUNTRY, country);
					session.setAttribute(CommandParameter.ACTOR, actor);
					session.setAttribute(CommandParameter.PRODUCER1, producer);
					session.setAttribute(CommandParameter.YEAR, year);
					session.setAttribute(CommandParameter.GENRES, genre);
					session.setAttribute(CommandParameter.GOAL, goal);
					session.setAttribute(CommandParameter.TARGET, goal);

				} else {
					session.setAttribute(CommandParameter.TARGET,  CommandParameter.MAIN);
					request.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);
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
