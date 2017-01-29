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
import by.zhdanovich.rat.entity.Comment;
import by.zhdanovich.rat.service.IClientService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code TakeCommentsOfFilmCommand} finds all the comments some film.
 * 
 * It implements the {@code ICommand} interface and implements its only method.
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 *
 */
public class TakeCommentsFilmCommand implements ICommand {
	/**
	 * Find all comments some film.
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
		HttpSession session = request.getSession();

		try {
			String filmUser = request.getParameter(CommandParameter.FILM_UID);
			String pageIn = request.getParameter(CommandParameter.PAGE);

			int page = CommandParameter.PAGE_DEFAULT;
			if (Validator.check(pageIn)) {
				page = Integer.parseInt(pageIn);
			}
			if (Validator.check(filmUser)) {
				ServiceFactory sevice = ServiceFactory.getInstance();
				IClientService client = sevice.getClientService();

				List<Comment> list = new ArrayList<Comment>();
				int noOfRecords = client.takeCommentOfFilm(Integer.parseInt(filmUser), list,
						(page - 1) * CommandParameter.RECORDS_PER_PAGE, CommandParameter.RECORDS_PER_PAGE);

				if (noOfRecords != 0) {
					int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / CommandParameter.RECORDS_PER_PAGE);
					request.setAttribute(CommandParameter.COMMENTS, list);
					session.setAttribute(CommandParameter.NO_OF_PAGES, noOfPages);
					session.setAttribute(CommandParameter.CURRENT_PAGES, page);
					session.setAttribute(CommandParameter.FILM_UID, filmUser);
					session.setAttribute(CommandParameter.TARGET, CommandParameter.COMMENT_FILM);
					carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);
					carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
				} else {
					session.setAttribute(CommandParameter.TARGET, CommandParameter.PERSONAL);
					session.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);
					carrier.put(CommandParameter.METHOD, CommandParameter.SEND_REDIRECT);
				}
			} else {
				request.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
				session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
				carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
				carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);
			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}
	}

}
