package by.zhdanovich.rat.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

/**
 * Class {@code CheckSessionFilter}implements the {@code Filter}. There is a
 * filter, which is used for cleaning session parameters.
 * 
 * @author Anna
 *
 */
@WebFilter(urlPatterns = { "/*" })
public class ClearSessionFilter implements Filter {

	private static final String TARGET = "target";
	private static final String GET = "GET";
	private static final String ERROR = "error";
	private static final String ERROR_DATA = "errorData";
	private static final String ERROR_ADD = "errorAdd";
	private static final String ERROR_SEARCH = "errorSerch";
	private static final String ERROR_STATUS_MESSAGE = "errorStatusMessage";
	private static final String ERROR_LOGIN_PASS_MESSAGE = "errorLoginPassMessage";
	private static final String ERROR_INPUT_DATA = "errorInputData";
	private static final String ADD_REQUEST = "addRequest";
	private static final String ERROR_AUTHORISATION_MESSAGE = "errorAuthorisationMessage";
	private static final String ERROR_FREE_LOGIN = "errorFreeLogin";
	private static final String ADD_MESSAGE = "addMessage";
	private static final String BLOCKING_MESSAGE = "blokingMessage";
	private static final String ERROR_GIVE_ASSESSMENT = "errorGiveAssessment";
	private static final String ERROR_GIVE_COMMENT = "errorGiveComment";
	private static final String MESSAGE_UPDATE_USER = "messageUpdateUser";

	public ClearSessionFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		session.setAttribute(TARGET, null);
		if (!req.getMethod().equals(GET)) {
			session.setAttribute(MESSAGE_UPDATE_USER, null);
			session.setAttribute(ERROR_DATA, null);
			session.setAttribute(ERROR_AUTHORISATION_MESSAGE, null);
			session.setAttribute(ERROR_STATUS_MESSAGE, null);
			session.setAttribute(ERROR_LOGIN_PASS_MESSAGE, null);
			session.setAttribute(ERROR_INPUT_DATA, null);
			session.setAttribute(ERROR_FREE_LOGIN, null);
			session.setAttribute(ADD_REQUEST, null);
			session.setAttribute(ADD_MESSAGE, null);
			session.setAttribute(ERROR_ADD, null);
			session.setAttribute(BLOCKING_MESSAGE, null);
			session.setAttribute(ERROR, null);
			session.setAttribute(ERROR_SEARCH, null);
			session.setAttribute(ERROR_GIVE_ASSESSMENT, null);
			session.setAttribute(ERROR_GIVE_COMMENT, null);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
