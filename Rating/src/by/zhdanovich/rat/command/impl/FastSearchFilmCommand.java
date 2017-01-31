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
 * Class {@code FastSearchFilmCommand} performs the request (command) to find
 * film by title.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 */
public class FastSearchFilmCommand implements ICommand {
	/**
	 * Find film by title.
	 * 
	 * Checks all data from the request is correct, audited data is transmitted
	 * to the service level. The response is analyzed, further in session falls
	 * message about the outcome of the implementation.
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

		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);

		HttpSession session = request.getSession();

		try {

			String text = request.getParameter(CommandParameter.TEXT);
			String pageIn = request.getParameter(CommandParameter.PAGE);

			int page = CommandParameter.PAGE_DEFAULT;

			if (Validator.check(pageIn)) {
				page = Integer.parseInt(pageIn);
			}

			
				List<Film> list = new ArrayList<Film>();

				ServiceFactory sevice = ServiceFactory.getInstance();
				ICommonService commonService = sevice.getCommonService();

				int noOfRecords = commonService.findFilmFasty(list, text,
						(page - 1) * CommandParameter.RECORDS_PER_PAGE, CommandParameter.RECORDS_PER_PAGE);

				if (noOfRecords != 0) {
					request.setAttribute(CommandParameter.FILMS, list);
					int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / CommandParameter.RECORDS_PER_PAGE);
					session.setAttribute(CommandParameter.NO_OF_PAGES, noOfPages);
					session.setAttribute(CommandParameter.CURRENT_PAGES, page);
					session.setAttribute(CommandParameter.TEXT, text);
					session.setAttribute(CommandParameter.TARGET, CommandParameter.SEARCH_FILM_FASTY);
				} else {
					request.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);
					session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
				}
			

		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}

	}
}
