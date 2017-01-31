package by.zhdanovich.rat.controller.util;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The class is a  utility class.
 * 
 * The class, which is engaged in sending data of processing request of client.
 * 
 * @author Anna
 *
 */
public class Sender {

	/**
	 * Here defined way to go to the client's page.
	 * 
	 * Based on the analysis of the data collection on map.
	 * 
	 * @param request
	 * @param response
	 * @param carrier
	 * @throws ServletException
	 * @throws IOException
	 */
	public void send(HttpServletRequest request, HttpServletResponse response, Carrier carrier)
			throws ServletException, IOException {
		RequestDispatcher dispatcher;
		switch (carrier.get(ControllerParameter.METHOD)) {
		case ControllerParameter.FORWARD:
			    takePreviousUrl(request);
			if (carrier.get(ControllerParameter.PAGE) != null && !carrier.get(ControllerParameter.PAGE).isEmpty()) {
				dispatcher = request.getRequestDispatcher(carrier.get(ControllerParameter.PAGE).trim());
				dispatcher.forward(request, response);
			} else {
				String page = ControllerParameter.PATH_ERROR;
				dispatcher = request.getRequestDispatcher(page);
				dispatcher.forward(request, response);
			}
			break;
		case ControllerParameter.SEND_REDIRECT:
			HttpSession session = request.getSession(false);
			String url = (String) session.getAttribute(ControllerParameter.PREVIOUS_URL);
			if (url != null && !url.isEmpty()) {
				response.sendRedirect(url);
			} else {
				response.sendRedirect(request.getContextPath() + ControllerParameter.PATH_INDEX);
			}
			break;
		case ControllerParameter.ERROR:
			dispatcher = request.getRequestDispatcher(ControllerParameter.PATH_ERROR);
			dispatcher.forward(request, response);
			break;
		case ControllerParameter.NOT_ACT:
			return;
		default:
			dispatcher = request.getRequestDispatcher(ControllerParameter.PATH_ERROR);
			dispatcher.forward(request, response);
			break;
		}
	}

	/**
	 * Determine the last requested URL and is stored in the session.
	 * 
	 * So you can use it in the case of sending a response to the client by
	 * redirect.
	 * 
	 * @param request
	 */
	private void takePreviousUrl(HttpServletRequest request) {
		String requestURL = request.getRequestURL().toString();
		Map<String, String[]> params = request.getParameterMap();
		Set<String> keys = params.keySet();
		String url = requestURL;
		int count = 0;
		for (String k : keys) {
			if (count == 0) {
				url += ControllerParameter.QUEST_MARK + k + ControllerParameter.EQ + params.get(k)[0];
			} else {

				url += ControllerParameter.AMPERSAND + k + ControllerParameter.EQ + params.get(k)[0];
			}
			count++;
		}
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute(ControllerParameter.PREVIOUS_URL, url);
		}
	}
}
