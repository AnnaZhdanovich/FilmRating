package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.Carrier;
import by.zhdanovich.rat.service.IClientService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code GiveAssessmentCommand} performs the request (command) to add a
 * new assessment of user to database.
 * 
 * @author Anna It implements the {@code ICommand} interface and implements its
 *         only method
 *         {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 */
public class GiveAssessmentCommand implements ICommand {
	/**
	 * Adding a assessment to the database.
	 * 
	 * Check the user is logged in, and then checks all data from the request is
	 * correct, audited data is transmitted to the service level. The response
	 * is analyzed, further in session falls message about the outcome of the
	 * implementation.
	 * 
	 * @param request
	 *            request of user
	 * @param carrier
	 *            object which in itself contains the information on the basis of
	 *            which will be selected method of sending a response to client.
	 * @throws CommandException
	 * 
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {

		HttpSession session = request.getSession();

		carrier.put(CommandParameter.METHOD, CommandParameter.SEND_REDIRECT);

		if (Validator.checkAuthorisation(session)) {
			session.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}

		try {

			String idFilm = request.getParameter(CommandParameter.ID_FILM);
			String number = request.getParameter(CommandParameter.NUMBER);

			if (Validator.check(idFilm) && Validator.check(number)) {

				int userUid = (int) session.getAttribute(CommandParameter.PARAM_NAME_ID);

				ServiceFactory factory = ServiceFactory.getInstance();
				IClientService client = factory.getClientService();

				boolean result = client.giveAssessment(Integer.parseInt(idFilm), userUid,
						Integer.parseInt(request.getParameter(CommandParameter.NUMBER)));

				if (result) {
					session.setAttribute(CommandParameter.ADD_MESSAGE, CommandParameter.MESSAGE);

				} else {
					session.setAttribute(CommandParameter.ERROR_GIVE_ASSESSMENT, CommandParameter.MESSAGE);
				}
			} else {
				session.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);

			}

		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);

		}

	}

}
