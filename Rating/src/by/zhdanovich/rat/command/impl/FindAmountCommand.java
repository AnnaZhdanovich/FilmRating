package by.zhdanovich.rat.command.impl;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.ICommand;
import by.zhdanovich.rat.command.util.CommandParameter;
import by.zhdanovich.rat.controller.util.Carrier;
import by.zhdanovich.rat.service.IAdminService;

import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code AddFilmCommand} performs a request for the values of the number
 * of registered users and the number of downloaded movies.
 * 
 * @author Anna
 *
 */
public class FindAmountCommand implements ICommand {
	/**
	 * Find amount of registered users and amount of downloaded film.
	 * 
	 * @param request
	 *            request of user
	 * @param carrier
	 *            map which in itself contains the information on the basis of
	 *            which will be selected method of sending a response to client.
	 * @throws CommandException
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {

		HttpSession session = request.getSession(true);
		carrier.put(CommandParameter.METHOD, CommandParameter.NOT_ACT);
		ServiceFactory sevice = ServiceFactory.getInstance();
		IAdminService adminService = sevice.getAdminService();

		try {
			Map<String, Integer> amount = new HashMap<String, Integer>();
			amount.put(CommandParameter.AMOUNT_FILMS, null);
			amount.put(CommandParameter.AMOUNT_USERS, null);

			adminService.findAmount(amount);
			session.setAttribute(CommandParameter.AMOUNT_FILMS, amount.get(CommandParameter.AMOUNT_FILMS));
			session.setAttribute(CommandParameter.AMOUNT_USERS, amount.get(CommandParameter.AMOUNT_USERS));
		} catch (ServiceException e) {
			throw new CommandException("Wrong of executing the command ", e);
		}

	}

}
