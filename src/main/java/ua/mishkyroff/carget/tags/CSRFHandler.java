package ua.mishkyroff.carget.tags;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by U on 29.07.2016.
 */
public class CSRFHandler extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    private String name;

    @Override
    public int doStartTag() throws JspException {
        try {
            HttpSession session = pageContext.getSession();
            Long token = (Long) session.getAttribute("csrfToken");
            pageContext.getOut().write("<input type=\"hidden\" name=\"" + name + "\" value=\"" +
                    token + "\" />");
        } catch (IOException e) {
            LOGGER.error("error in CSRFtag, writing token" + e);
        }
        return super.doStartTag();
    }
    public void setName(String name) {
        this.name = name;
    }
}
