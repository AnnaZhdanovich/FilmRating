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
 * Class {@code CommentsOfUserCommand} allows to search comments of some film.
 * 
 * @author Anna
 */
public class CommentsOfUserCommand implements ICommand {
	/**
	 * Search film comments.
	 * 
	 * Method checks all data from the request is correct, audited data is
	 * transmitted to the service level. The response is analyzed, further in
	 * session falls message about the outcome of the implementation.
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
		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);
		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);

		if (Validator.checkAuthorisation(session)) {
			request.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}

		try {

			String idUser = request.getParameter(CommandParameter.UID_USER);
			String pageIn = request.getParameter(CommandParameter.PAGE);

			int page = CommandParameter.PAGE_DEFAULT;
			if (Validator.check(pageIn))
				page = Integer.parseInt(pageIn);

			if (Validator.check(idUser)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IClientService client = sevice.getClientService();

				List<Comment> list = new ArrayList<Comment>();
				int noOfRecords = client.takeComment(Integer.parseInt(idUser), list,
						(page - 1) * CommandParameter.RECORDS_PER_PAGE, CommandParameter.RECORDS_PER_PAGE);

				session.setAttribute(CommandParameter.TARGET, CommandParameter.COMMENTS);

				if (noOfRecords != 0) {
					int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / CommandParameter.RECORDS_PER_PAGE);
					request.setAttribute(CommandParameter.COMMENTS, list);
					session.setAttribute(CommandParameter.NO_OF_PAGES, noOfPages);
					session.setAttribute(CommandParameter.CURRENT_PAGES, page);
					session.setAttribute(CommandParameter.UID_USER, idUser);

				} else {
					request.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);
					session.setAttribute(CommandParameter.TARGET, CommandParameter.COMMENTS);
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
