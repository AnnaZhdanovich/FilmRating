package by.zhdanovich.rat.tag;

import java.io.IOException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Class {@code TableTag} receives values rows attribute and generates a table
 * with the specified number of rows.
 * 
 * @author Anna
 */

public class TableTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String head;
	private int rows;

	public void setHead(String head) {
		this.head = head;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	@Override
	public int doStartTag() throws JspTagException {
		try {
			JspWriter out = pageContext.getOut();
			out.write(
					"<table width='100%' bordercolor='#708496' height='100' border='3'><colgroup span='2' title='title'/>");
			out.write("<thead><tr><th scope='col'>" + head + "</th></tr></thead>");
			out.write("<tbody><tr><td align='center'>");
		} catch (IOException e) {
			throw new JspTagException(e.getMessage());
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doAfterBody() throws JspTagException {
		if (rows--> 1) {
			try {
				pageContext.getOut().write("</td></tr><tr><td align='center'>");
			} catch (IOException e) {
				throw new JspTagException(e.getMessage());
			}
			return EVAL_BODY_AGAIN;
		} else {
			return SKIP_BODY;
		}
	}

	@Override
	public int doEndTag() throws JspTagException {
		try {
			pageContext.getOut().write("</td></tr></tbody></table>");
		} catch (IOException e) {
			throw new JspTagException(e.getMessage());
		}
		return EVAL_PAGE;
	}
}
