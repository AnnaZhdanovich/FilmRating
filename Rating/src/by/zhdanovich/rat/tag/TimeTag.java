package by.zhdanovich.rat.tag;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Class {@code TimeTag} is used to create a custom tag. The tag sends to the
 * client about the current time and locale information.
 * 
 * @author Anna
 */
public class TimeTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {

		Date curTime = new Date();
		DateFormat dtfrm = DateFormat.getDateTimeInstance();
		String dateTime = dtfrm.format(curTime);

		String time = "<b> " + dateTime + " </b>";
		try {
			JspWriter out = pageContext.getOut();
			out.write(time);
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

}
