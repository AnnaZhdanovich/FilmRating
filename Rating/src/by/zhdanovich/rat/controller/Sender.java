package by.zhdanovich.rat.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The class, which is engaged in sending data of processing request of client.
 * 
 * @author Anna
 *
 */
class Sender {
	private static final String METHOD = "method";
	private static final String SEND_REDIRECT = "sendRedirect";
	private static final String FORWARD = "forward";
	private static final String PAGE = "page";
	private static final String PREVIOUS_URL = "previousUrl";
	private static final String NOT_ACT = "noAct";
	private static final String AMPERSAND = "&";
	private static final String QUEST_MARK = "?";
	private static final String EQ = "=";
	private static final String PATH_ERROR = "/jsp/error/error.jsp";
	private static final String PATH_INDEX = "/index.jsp";

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
	void send(HttpServletRequest request, HttpServletResponse response, Carrier carrier)
			throws ServletException, IOException {
		switch (carrier.get(METHOD)) {
		case FORWARD:
			takePreviousUrl(request);
			if (carrier.get(PAGE) != null && !carrier.get(PAGE).isEmpty()) {
				RequestDispatcher dispatcher = request.getRequestDispatcher(carrier.get(PAGE).trim());
				dispatcher.forward(request, response);
			} else {
				String page = PATH_ERROR;
				RequestDispatcher dispatcher = request.getRequestDispatcher(page);
				dispatcher.forward(request, response);
			}
			break;
		case SEND_REDIRECT:
			HttpSession session = request.getSession(false);
			String url = (String) session.getAttribute(PREVIOUS_URL);
			if (url != null && !url.isEmpty()) {
				response.sendRedirect(url);
			} else {
				response.sendRedirect(request.getContextPath() + PATH_INDEX);
			}
			break;
		case NOT_ACT:
			return;
		default:
			RequestDispatcher dispatcher = request.getRequestDispatcher(PATH_ERROR);
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
				url += QUEST_MARK + k + EQ + params.get(k)[0];
			} else {

				url += AMPERSAND + k + EQ + params.get(k)[0];
			}
			count++;
		}
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute(PREVIOUS_URL, url);
		}
	}
}
