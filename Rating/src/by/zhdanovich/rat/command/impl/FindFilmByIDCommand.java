package by.zhdanovich.rat.command.impl;

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
 * Class {@code FindFilmByIDCommand} performs a request to search the film by
 * its ID.
 * 
 * @author Anna
 *
 */
public class FindFilmByIDCommand implements ICommand {
	/**
	 * Search the film by its ID.
	 * 
	 * All data from the request are checked for correctness, audited data is
	 * transmitted to the service level. The response is analyzed, further in
	 * session falls message about the outcome of the implementation.
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

		HttpSession session = request.getSession();
		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);
		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
		try {
			String idFilm = request.getParameter(CommandParameter.PARAM_NAME_ID);

			if (Validator.check(idFilm)) {
				int id = Integer.parseInt(idFilm);

				ServiceFactory sevice = ServiceFactory.getInstance();
				ICommonService commonService = sevice.getCommonService();

				Film film;
				film = commonService.findFilmById(id);

				if (film != null) {
					request.setAttribute(CommandParameter.FILM, film);
					session.setAttribute(CommandParameter.TARGET, CommandParameter.INFO_FILM);
					session.setAttribute(CommandParameter.ID_FILM, idFilm);
				} else {
					session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
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
