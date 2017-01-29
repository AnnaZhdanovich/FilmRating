package by.zhdanovich.rat.command.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.controller.Carrier;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code AsideFilmCommand} perform a request for search the most talked
 * about films.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 */

public class DiscussedFilmCommand implements ICommand {
	/**
	 * Find the most talked about films.
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

		HttpSession session = request.getSession(true);

		carrier.put(CommandParameter.METHOD, CommandParameter.NOT_ACT);

		ServiceFactory sevice = ServiceFactory.getInstance();
		ICommonService commonService = sevice.getCommonService();

		try {
			List<Film> films = new ArrayList<Film>();
			commonService.findFilm(films);
			session.setAttribute(CommandParameter.ASSIDE_FILMS, films);
		} catch (ServiceException e) {
			throw new CommandException("Wrong of servlet when executing the command", e);
		}
	}
}