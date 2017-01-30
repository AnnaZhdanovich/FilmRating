package by.zhdanovich.rat.command.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.util.Carrier;
import by.zhdanovich.rat.entity.User;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code FindUsersListCommand} of perform a request to find all users.
 * 
 * It implements the {@code ICommand} interface and implements its only method.
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException}
 * 
 * @author Anna
 *
 */
public class FindUsersListCommand implements ICommand {
	/**
	 * Find all users.
	 * 
	 * Check whether the user is logged in, and then checks all data from the
	 * request is correct, audited data is transmitted to the service level. The
	 * response is analyzed, further in session falls message about the outcome
	 * of the implementation.
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

		String pageIn = request.getParameter(CommandParameter.PAGE);
		HttpSession session = request.getSession();
		if (Validator.checkAuthorisation(session)) {
			request.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}
		try {
			int page = CommandParameter.PAGE_DEFAULT;
			if (Validator.check(pageIn)) {
				page = Integer.parseInt(pageIn);
			}
			List<User> list = new ArrayList<User>();
			ServiceFactory sevice = ServiceFactory.getInstance();
			ICommonService common = sevice.getCommonService();

			int noOfRecords = common.findUsersList(list, (page - 1) * CommandParameter.RECORDS_PER_PAGE,
					CommandParameter.RECORDS_PER_PAGE);
			session.setAttribute(CommandParameter.TARGET, CommandParameter.USERS_LIST);
			if (noOfRecords != 0) {
				int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / CommandParameter.RECORDS_PER_PAGE);
				session.setAttribute(CommandParameter.NO_OF_PAGES, noOfPages);
				session.setAttribute(CommandParameter.CURRENT_PAGES, page);
				request.setAttribute(CommandParameter.USERS, list);

			} else {
				request.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);
			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);

		}
	}

}
