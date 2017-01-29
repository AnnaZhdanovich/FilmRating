package by.zhdanovich.rat.command.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.controller.Carrier;
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code LocalizationCommand} is used to search  comments of films  that
 * was added by user.
 * 
 * @author Anna
 *
 */
public class PreviousCommentsCommand implements ICommand {

	/**
	 * Execution of a request for search recently added  comments of films.
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
			List<Comment> comments = new ArrayList<Comment>();
			commonService.findFilmComment(comments);
			session.setAttribute(CommandParameter.ASSIDE_COMMENTS, comments);
		} catch (ServiceException e) {
			throw new CommandException("Wrong of executing the command ", e);
		}
	}
}
