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
import by.zhdanovich.rat.entity.Personality;
import by.zhdanovich.rat.service.ICommonService;
import by.zhdanovich.rat.service.exception.ServiceException;
import by.zhdanovich.rat.service.factory.ServiceFactory;

/**
 * Class {@code FindOfPersonalityCommand} allows to find all the actors and
 * directors.
 * 
 * @author Anna
 *
 */
public class FindPersonalityCommand implements ICommand {
	/**
	 * Search all the actors or directors.
	 *
	 * Checks all data from the request is correct, audited data is transmitted
	 * to the service level of the response is analyzed further in session falls
	 * message about the outcome of the implementation.
	 * 
	 * @param request
	 *            request of user
	 * @param carrier
	 *            object which in itself contains the information on the basis of
	 *            which the selected method of sending a response to client.
	 * @throws CommandException
	 */
	@Override
	public void execute(HttpServletRequest request, Carrier carrier) throws CommandException {
		carrier.put(CommandParameter.METHOD, CommandParameter.FORWARD);
		carrier.put(CommandParameter.PAGE, CommandParameter.PATH_START_USER);
		HttpSession session = request.getSession();

		try {
			String goal = request.getParameter(CommandParameter.GOAL);
			if (Validator.check(goal)) {
				List<Personality> actors = new ArrayList<Personality>();
				List<Personality> producers = new ArrayList<Personality>();

				ServiceFactory sevice = ServiceFactory.getInstance();
				ICommonService commonService = sevice.getCommonService();

				commonService.takePersonality(actors, producers);

				session.setAttribute(CommandParameter.ACTORS, actors);
				session.setAttribute(CommandParameter.PRODUCERS, producers);
				session.setAttribute(CommandParameter.TARGET, goal);

			} else {
				request.setAttribute(CommandParameter.ERROR_DATA, CommandParameter.MESSAGE);
				session.setAttribute(CommandParameter.TARGET, CommandParameter.MAIN);
			}

		} catch (ServiceException e) {
			throw new CommandException("Wrong executing the command", e);

		}
	}
}
