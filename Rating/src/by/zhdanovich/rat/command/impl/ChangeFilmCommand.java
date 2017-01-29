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
import by.zhdanovich.rat.entity.Personality;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code ChangeFilmCommand} performs the request (command) to find data
 * of film and return it to page of client for changing.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 */
public class ChangeFilmCommand implements ICommand {
	/**
	 * Find information about the film by the identifier.
	 * 
	 * User authorization check, and then checks all data from the request is
	 * correct, audited data is transmitted to the service level of the response
	 * is analyzed further in session falls message about the outcome of the
	 * implementation.
	 * 
	 * @param request
	 *            request of user
	 * @param carrier
	 *            object which in itself contains the information on the basis
	 *            of which the selected method of sending a response to client.
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
			String idFilm = request.getParameter(CommandParameter.PARAM_NAME_ID);

			if (Validator.check(idFilm)) {
				ServiceFactory sevice = ServiceFactory.getInstance();
				ICommonService common = sevice.getCommonService();

				List<Personality> actors = new ArrayList<Personality>();
				List<Personality> producers = new ArrayList<Personality>();

				common.takePersonality(actors, producers);

				request.setAttribute(CommandParameter.ACTORS, actors);
				request.setAttribute(CommandParameter.PRODUCERS, producers);

				Film film;
				film = common.findFilmById(Integer.parseInt(idFilm));
				if (film != null) {
					request.setAttribute(CommandParameter.FILM, film);
					session.setAttribute(CommandParameter.TARGET, CommandParameter.CHANGE_FILM);
				} else {
					session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
					request.setAttribute(CommandParameter.ERROR, CommandParameter.MESSAGE);
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
