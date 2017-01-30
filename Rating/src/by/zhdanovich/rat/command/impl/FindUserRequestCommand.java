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
import by.zhdanovich.rat.entity.RequestOfUser;
import by.zhdanovich.rat.service.IAdminService;

import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code FindUserRequestCommand} of perform a request to search all users
 * questions to administrator.
 * 
 * It implements the {@code ICommand} interface and implements its only method.
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException}
 * 
 * @author Anna
 *
 */
public class FindUserRequestCommand implements ICommand {
	/**
	 * Find all new request of users to administrator.
	 * 
	 * User authorization check, and then checks all data from the request is
	 * correct, audited data is transmitted to the service level. The response
	 * is analyzed, further in session falls message about the outcome of the
	 * implementation.
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

		if (Validator.checkAuthorisation(session)) {
			request.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}
		try {

			String pageIn = request.getParameter(CommandParameter.PAGE);
			int page = CommandParameter.PAGE_DEFAULT;

			if (Validator.check(pageIn)) {
				page = Integer.parseInt(pageIn);
			}
			ServiceFactory sevice = ServiceFactory.getInstance();
			IAdminService adminService = sevice.getAdminService();

			List<RequestOfUser> list = new ArrayList<RequestOfUser>();

			int noOfRecords = adminService.findUserRequests(list, (page - 1) * CommandParameter.RECORDS_PER_PAGE,
					CommandParameter.RECORDS_PER_PAGE);

			session.setAttribute(CommandParameter.TARGET, CommandParameter.LIST_REQUESTS);

			if (noOfRecords != 0) {
				int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / CommandParameter.RECORDS_PER_PAGE);
				request.setAttribute(CommandParameter.LIST_REQUESTS, list);
				session.setAttribute(CommandParameter.NO_OF_PAGES, noOfPages);
				session.setAttribute(CommandParameter.CURRENT_PAGES, page);

			} else {
				request.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);
				session.setAttribute(CommandParameter.TARGET, CommandParameter.LIST_REQUESTS);
			}

		} catch (ServiceException e) {
			throw new CommandException("command message", e);
		} catch (IllegalStateException e) {
			throw new CommandException("command message", e);

		}
	}

}
