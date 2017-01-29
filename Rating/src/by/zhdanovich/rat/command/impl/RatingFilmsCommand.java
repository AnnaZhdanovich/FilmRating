package by.zhdanovich.rat.command.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.Carrier;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code RatingFilmCommand} is used to determine the rating of users.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 */

public class RatingFilmsCommand implements ICommand {
	/**
	 * To determine the rating of films.
	 * 
	 * @param request
	 *            request of user
	 * @param carrier
	 *            object which in itself contains the information on the basis of
	 *            which will be selected method of sending a response to clint.
	 * @throws CommandException
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {

		HttpSession session = request.getSession();
		String pageIn = request.getParameter(CommandParameter.PAGE);
		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);

		try {
			int page = CommandParameter.PAGE_DEFAULT;
			if (Validator.check(pageIn)) {
				page = Integer.parseInt(pageIn);
			}
			List<Film> list = new ArrayList<Film>();

			String type = request.getParameter(CommandParameter.TYPE);
			String goal = request.getParameter(CommandParameter.GOAL);

			if (Validator.check(type) && Validator.check(goal)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				ICommonService commonService = sevice.getCommonService();

				int noOfRecords = commonService.findFilmsByRating(list, type,
						(page - 1) * CommandParameter.RECORDS_PER_PAGE, CommandParameter.RECORDS_PER_PAGE);

				if (noOfRecords != 0) {
					int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / CommandParameter.RECORDS_PER_PAGE);
					session.setAttribute(CommandParameter.NO_OF_PAGES, noOfPages);
					session.setAttribute(CommandParameter.CURRENT_PAGES, page);
					request.setAttribute(CommandParameter.FILMS, list);
					session.setAttribute(CommandParameter.TARGET, goal);
					session.setAttribute(CommandParameter.TYPE, type);
					session.setAttribute(CommandParameter.GOAL, goal);
					carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
				} else {
					request.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);
					carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
				}
			} else {

			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);

		}

	}

}
