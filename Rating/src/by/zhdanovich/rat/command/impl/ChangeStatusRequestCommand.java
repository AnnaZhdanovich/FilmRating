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
 * Class {@code MarkerOfRequestCommand} handles the request to change the status
 * of the client's problem.
 * 
 * @author Anna
 *
 */
public class ChangeStatusRequestCommand implements ICommand {
	/**
	 * Update status of request user.
	 * 
	 * Checks the user is logged in, and then checks all data from the request
	 * is correct, audited data is transmitted to the service level. The
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
	 * 
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {

		try {
			carrier.put(CommandParameter.METHOD, CommandParameter.SEND_REDIRECT);
			HttpSession session = request.getSession();

			if (Validator.checkAuthorisation(session)) {
				session.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);				
				return;
			}
			
			String id = request.getParameter(CommandParameter.ID_REQUEST);
			String type = request.getParameter(CommandParameter.TYPE);

			if (Validator.check(id) && Validator.check(type)) {
				int idRequest = Integer.parseInt(id);

				boolean result = false;

				ServiceFactory sevice = ServiceFactory.getInstance();
				IAdminService admin = sevice.getAdminService();

				result = admin.changeStatusRequest(idRequest, type);

				if (result) {
					session.setAttribute(CommandParameter.BLOKING_MESSAGE, CommandParameter.MESSAGE);
				} else {
					session.setAttribute(CommandParameter.ERROR_UPDATE_MESSAGE, CommandParameter.MESSAGE);
				}
			} else {
				session.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MAIN);
			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);

		}
	}

}
