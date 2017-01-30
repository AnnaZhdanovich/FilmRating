package by.zhdanovich.rat.command.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.controller.util.Carrier;
import by.zhdanovich.rat.entity.Film;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code NewFilmsCommand} is used to search to find the newly added film.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 *
 */
public class NewFilmsCommand implements ICommand {
	/**
	 * Find the newly added film.
	 * 
	 * @param request
	 *            request of user
	 * @param carrie
	 *            object which in itself contains the information on the basis of
	 *            which will be selected method of sending a response to client.
	 * @throws CommandException
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {

		carrier.put(CommandParameter.METHOD, CommandParameter.NOT_ACT);

		try {
			HttpSession session = request.getSession(true);

			ServiceFactory sevice = ServiceFactory.getInstance();
			ICommonService commonService = sevice.getCommonService();

			List<Film> list = new ArrayList<Film>();

			commonService.findNewFilms(list);

			session.setAttribute(CommandParameter.NEW_FILM_LIST, list);
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}
	}
}
