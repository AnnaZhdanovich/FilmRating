package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.Carrier;
import by.zhdanovich.rat.service.IAdminService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code UpdateFilmPersonalityCommand} processes the request to update
 * actor or producer.
 *
 * It implements the {@code ICommand} interface and implements its only method.
 * {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 * 
 * @author Anna
 *
 */
public class UpdateFilmPersonalityCommand implements ICommand {
	/**
	 * Update actor or producer.
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
			String personUid = request.getParameter(CommandParameter.PERSON_UID);
			String fmUid = request.getParameter(CommandParameter.FILM_UID);
			String action = request.getParameter(CommandParameter.ACTION);
			if (Validator.check(personUid) && Validator.check(fmUid) && Validator.check(action)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IAdminService adminService = sevice.getAdminService();

				boolean result = adminService.updatePersonality(Integer.parseInt(fmUid), Integer.parseInt(personUid),
						action);

				if (result) {
					session.setAttribute(CommandParameter.MESSAGE_UPDATE_USER, CommandParameter.MESSAGE);
				} else {
					session.setAttribute(CommandParameter.ERROR_UPDATE_MESSAGE, CommandParameter.MESSAGE);
				}
			} else {
				session.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}
	}
}
