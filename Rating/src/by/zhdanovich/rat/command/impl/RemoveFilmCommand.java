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
 * Class {@code RemoveFilmCommand} used to add film to archive.
 * 
 * @author Anna
 *
 */
public class RemoveFilmCommand implements ICommand {
	/**
	 * Add film to archive.
	 * 
	 * At first user authorization check, and then checks all data from the
	 * request is correct, audited data is transmitted to the service level.
	 * The response is analyzed, further in session falls message about the
	 * outcome of the implementation.
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
			request.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
			return;
		}

		try {
			String idFilm = request.getParameter(CommandParameter.FILM_UID);
			String typeFilm = request.getParameter(CommandParameter.TYPE);
			if (Validator.check(idFilm)) {

				ServiceFactory sevice = ServiceFactory.getInstance();
				IAdminService adminService = sevice.getAdminService();

				boolean result;

				result = adminService.deleteFilm(Integer.parseInt(idFilm), typeFilm);

				if (!result) {

					request.setAttribute(CommandParameter.ERROR, CommandParameter.MESSAGE);
				}
			}
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}
	}
}
