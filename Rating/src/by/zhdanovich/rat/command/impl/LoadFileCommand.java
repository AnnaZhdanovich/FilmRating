package by.zhdanovich.rat.command.impl;

import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.command.util.Validator;
import by.zhdanovich.rat.controller.Carrier;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Class {@code AddFilmCommand} performs the request (command) to load new file
 * to database.
 * 
 * @author Anna It implements the {@code ICommand} interface and implements its
 *         only method
 *         {@code execute(HttpServletRequest request, Carrier carrier) throws CommandException }
 */
public class LoadFileCommand implements ICommand {
	/**
	 * Loading new file to database.
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

		carrier.put(CommandParameter.METHOD, CommandParameter.SEND_REDIRECT);

		HttpSession session = request.getSession();

		try {
			if (Validator.checkAuthorisation(session)) {
				session.setAttribute(CommandParameter.ERROR_AUTHORISATION_MESSAGE, CommandParameter.MESSAGE);
				return;
			}
			int id = 0;
			String type = request.getParameter(CommandParameter.TYPE);
			if (Validator.check(type)) {

				if (type.equals(CommandParameter.FILM)
						&& Validator.check(request.getParameter(CommandParameter.FILM_UID))) {

					id = Integer.parseInt(request.getParameter(CommandParameter.FILM_UID));

				} else {

					if (type.equals(CommandParameter.USER)) {
						id = (int) session.getAttribute(CommandParameter.PARAM_NAME_ID);

					} else {
						session.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
						return;
					}
				}

				final String path = request.getServletContext().getRealPath("images");

				if (request.getPart(CommandParameter.FILE) != null) {
					Part filePart = request.getPart(CommandParameter.FILE);
					ServiceFactory sevice = ServiceFactory.getInstance();
					ICommonService commonService = sevice.getCommonService();

					boolean result = commonService.addImage(filePart, path, id, type);

					if (result) {
						session.setAttribute(CommandParameter.MESSAGE_UPDATE_USER, CommandParameter.MESSAGE);

					} else {
						session.setAttribute(CommandParameter.ERROR_UPDATE_MESSAGE, CommandParameter.MESSAGE);

					}
				} else {
					session.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
					return;
				}
			}

		} catch (ServletException e) {
			throw new CommandException("Wrong of servlet when executing the command", e);
		} catch (IOException e) {
			throw new CommandException("Wrong I/O when executing the command", e);
		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);
		}

	}
}
