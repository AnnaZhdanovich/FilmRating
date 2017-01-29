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
import by.zhdanovich.rat.service.IAdminService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code FindAddedCommentCommand} performs request search for new
 * comments to audit their admin.
 * 
 * @author Anna
 *
 */
public class FindAddedCommentCommand implements ICommand {
	/**
	 * Find new comments for film.
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

		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);

		try {
			if (Validator.checkAuthorisation(session)) {
				request.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
				return;
			}
			String pageIn = request.getParameter(CommandParameter.PAGE);
			int page = CommandParameter.PAGE_DEFAULT;
			if (Validator.check(pageIn)) {
				page = Integer.parseInt(pageIn);
			}
			List<Comment> list = new ArrayList<Comment>();
			ServiceFactory sevice = ServiceFactory.getInstance();
			IAdminService adminService = sevice.getAdminService();

			int noOfRecords = adminService.findAddedComment(list, (page - 1) * CommandParameter.RECORDS_PER_PAGE,
					CommandParameter.RECORDS_PER_PAGE);
			session.setAttribute(CommandParameter.TARGET, CommandParameter.ADDED_COMMENT);
			if (noOfRecords != 0) {
				int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / CommandParameter.RECORDS_PER_PAGE);
				session.setAttribute(CommandParameter.NO_OF_PAGES, noOfPages);
				session.setAttribute(CommandParameter.CURRENT_PAGES, page);
				request.setAttribute(CommandParameter.COMMENT, list);

			} else {
				request.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);
			}

		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);

		}

	}

}
