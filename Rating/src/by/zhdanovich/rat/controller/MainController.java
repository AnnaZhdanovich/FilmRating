package by.zhdanovich.rat.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.zhdanovich.rat.controller.util.Sender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.zhdanovich.rat.command.exception.CommandException;
import by.zhdanovich.rat.command.factory.CommandFactory;
import by.zhdanovich.rat.controller.util.Carrier;
import by.zhdanovich.rat.controller.util.ControllerParameter;
import by.zhdanovich.rat.command.ICommand;

/**
 * The class of servlet which performs the function of the controller.
 * 
 * Data access is through the controllers. The controller processes the user's
 * request and determines what type of return you need to display to the user
 * and what data it is necessary to ask the model.
 * 
 * @author Anna
 *
 */
@WebServlet(name = "MainController", urlPatterns = { "/MainController" })
@MultipartConfig
public class MainController extends HttpServlet {
	private static Logger log = LogManager.getLogger(MainController.class);
	private static final long serialVersionUID = 1L;

	public MainController() {
		super();
	}

	/**
	 * This method handles request GET.
	 * 
	 * @param request
	 *            request of client
	 * @param response
	 *            response to client
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * This method handles request POST.
	 * 
	 * @param request
	 *            request of client
	 * @param response
	 *            response to client
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * The method receives commands specific object calls the execute method on
	 * it, and transmits the result to the sender
	 * 
	 * @see by.zhdanovich.rat.controller.Carrier
	 * @see by.zhdanovich.rat.controller.Sender
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * 
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Carrier carrier = new Carrier();
		CommandFactory factory = new CommandFactory();
		ICommand command = factory.defineCommand(request);
		
		try {
			if(command!=null){
			command.execute(request, carrier);
			}
		} catch (CommandException e) {
			log.error("You got a mistake in execute command", e);
			carrier.put(ControllerParameter.METHOD, ControllerParameter.ERROR);
		}
		Sender sender = new Sender();
		sender.send(request, response, carrier);
	}
}
