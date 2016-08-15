package ua.mishkyroff.carget.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.entities.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter checks session parameters and sets it to default values if they are invalid
 * Also this filter sets language to user's default value from browser if other not specified
 *
 * @author Anton Mishkyroff
 */
public class CheckSessionFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        //check session attributes and fill it with default params
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession hs = request.getSession(true);

        if (hs.getAttribute(SessionAttributes.USER_ROLE.toString()) == null) {
            hs.setAttribute(SessionAttributes.USER_ROLE.toString(), UserRole.GUEST);
        }
        if (hs.getAttribute(SessionAttributes.MESSAGE.toString()) == null) {
            hs.setAttribute(SessionAttributes.MESSAGE.toString(), "");
        }
        if (hs.getAttribute(SessionAttributes.USER_NAME.toString()) == null) {
            hs.setAttribute(SessionAttributes.USER_NAME.toString(), "");
        }
        if (hs.getAttribute(SessionAttributes.USER_ID.toString()) == null) {
            hs.setAttribute(SessionAttributes.USER_ID.toString(), "");
        }
        if (hs.getAttribute(SessionAttributes.LOCALE.toString()) == null) {
            if (request.getLocale().getLanguage().toUpperCase().equals("RU")){
                hs.setAttribute(SessionAttributes.LOCALE.toString(), "RU");
            }else{
                hs.setAttribute(SessionAttributes.LOCALE.toString(), "EN");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
