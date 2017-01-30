package by.zhdanovich.rat.command.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.controller.util.Carrier;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code RatingUsersCommand} is used to determine the rating of films.
 *
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 *
 */
public class RatingUsersCommand implements ICommand {
	/**
	 * To determine the rating of users..
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
		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);
		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
		String type = request.getParameter(CommandParameter.TYPE);
		String goal = request.getParameter(CommandParameter.GOAL);

		HttpSession session = request.getSession();

		try {
			
			
			List<User> list = new ArrayList<User>();

			ServiceFactory sevice = ServiceFactory.getInstance();
			ICommonService common = sevice.getCommonService();

			common.findUsersByRating(list,type);
			session.setAttribute(CommandParameter.TARGET, goal);
			
			if (! list.isEmpty()) {				
				request.setAttribute(CommandParameter.USERS, list);
				session.setAttribute(CommandParameter.TYPE, type);
				session.setAttribute(CommandParameter.GOAL, goal);
			} else {
				request.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);

			}

		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);

		}
	}

}
