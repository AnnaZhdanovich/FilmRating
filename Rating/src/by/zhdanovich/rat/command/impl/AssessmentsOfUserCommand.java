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
import by.zhdanovich.rat.entity.Assessment;
import by.zhdanovich.rat.service.IClientService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Command {@code AssessmentsOfUserCommand} perform a request for search all
 * assessments of films that have been exposed to a some user.
 * 
 * @author Anna
 *
 */
public class AssessmentsOfUserCommand implements ICommand {
	/**
	 * Performs a search request all assessments of films that have been exposed
	 * to a some user.
	 * 
	 * At first user authorization check, and then checks all data from the
	 * request is correct, audited data is transmitted The service level of the
	 * response is analyzed, further in session falls message about the outcome
	 * of the implementation.
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

		String idUser = request.getParameter(CommandParameter.UID_USER);
		String pageIn = request.getParameter(CommandParameter.PAGE);

		try {
			int page = CommandParameter.PAGE_DEFAULT;

			if (Validator.check(pageIn))
				page = Integer.parseInt(pageIn);

			if (Validator.check(idUser)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IClientService client = sevice.getClientService();

				List<Assessment> list = new ArrayList<Assessment>();

				int noOfRecords = client.takeAssessment(Integer.parseInt(idUser), list,
						(page - 1) * CommandParameter.RECORDS_PER_PAGE, CommandParameter.RECORDS_PER_PAGE);
				session.setAttribute(CommandParameter.TARGET, CommandParameter.ASSESSMENTS);
				if (noOfRecords != 0) {
					int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / CommandParameter.RECORDS_PER_PAGE);
					session.setAttribute(CommandParameter.NO_OF_PAGES, noOfPages);
					session.setAttribute(CommandParameter.CURRENT_PAGES, page);
					session.setAttribute(CommandParameter.UID_USER, idUser);
					request.setAttribute(CommandParameter.ASSESSMENTS, list);
				} else {
					session.setAttribute(CommandParameter.TARGET, CommandParameter.ASSESSMENTS);
					request.setAttribute(CommandParameter.ERROR_SEARCH, CommandParameter.MESSAGE);
				}
			} else {
				session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
				request.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
			}
		} catch (ServiceException e) {
			throw new CommandException("command message", e);
		}
	}
}
