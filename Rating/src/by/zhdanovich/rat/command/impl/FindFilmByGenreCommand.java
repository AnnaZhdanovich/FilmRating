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
import by.zhdanovich.rat.service.IClientService;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code FindFilmByGenreCommand} performs a request to search the film by
 * genre.
 * 
 * @author Anna
 *
 */

public class FindFilmByGenreCommand implements ICommand {

	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {
		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);
		HttpSession session = request.getSession();

		try {
			String pageIn = request.getParameter(CommandParameter.PAGE);
			String genre = request.getParameter(CommandParameter.GENRES);

			int page = CommandParameter.PAGE_DEFAULT;

			if (Validator.check(pageIn))
				page = Integer.parseInt(pageIn);

			if (Validator.check(genre)) {

				int idGanre = Integer.parseInt(genre);

				ServiceFactory sevice = ServiceFactory.getInstance();
				ICommonService commonService = sevice.getCommonService();

				commonService.colculateRating();

				IClientService clientService = sevice.getClientService();

				List<Film> list = new ArrayList<Film>();

				int noOfRecords = clientService.findFilmByGenre(idGanre, list,
						(page - 1) * CommandParameter.RECORDS_PER_PAGE, CommandParameter.RECORDS_PER_PAGE);

				session.setAttribute(CommandParameter.TARGET, CommandParameter.FIND_BY_GENRE);
				if (noOfRecords != 0) {
					int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / CommandParameter.RECORDS_PER_PAGE);
					session.setAttribute(CommandParameter.NO_OF_PAGES, noOfPages);
					session.setAttribute(CommandParameter.CURRENT_PAGES, page);
					request.setAttribute(CommandParameter.FILMS, list);
					session.setAttribute(CommandParameter.GENRES, genre);
				} else {
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
