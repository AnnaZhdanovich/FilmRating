package by.zhdanovich.rat.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.Carrier;
import by.zhdanovich.rat.entity.Personality.RoleOfActor;
import by.zhdanovich.rat.service.IAdminService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code AddPersonalityCommand} performs the request (command) to add a
 * new actor or producer.
 * 
 * It implements the {@code ICommand} interface and implements its only method
 * {@code execute(HttpServletRequest request, Map<String, String> map) throws CommandException }
 * 
 * @author Anna
 */
public class AddPersonalityCommand implements ICommand {
	/**
	 * Adding a new actor or producer to the database.
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
		carrier.put(CommandParameter.METHOD, CommandParameter.SEND_REDIRECT);

		if (Validator.checkAuthorisation(session)) {
			request.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}

		String firstname = request.getParameter(CommandParameter.FIRSTNAME);
		String lastname = request.getParameter(CommandParameter.LASTNAME);
		String role = request.getParameter(CommandParameter.ROLE);

		try {

			if (Validator.checkName(firstname) && Validator.checkName(lastname) && Validator.check(role)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IAdminService adminService = sevice.getAdminService();
				RoleOfActor roleActor = RoleOfActor.valueOf(role.toUpperCase());

				boolean result = adminService.addNewPersonality(firstname, lastname, roleActor);

				if (result) {
					session.setAttribute(CommandParameter.ADD_MESSAGE, CommandParameter.MESSAGE);
				} else {
					session.setAttribute(CommandParameter.ERROR_ADD, CommandParameter.MESSAGE);
				}
			} else {
				session.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
			}

		} catch (ServiceException e) {
			throw new CommandException("Wrong  execution the command ", e);
		}
		return;
	}
}
