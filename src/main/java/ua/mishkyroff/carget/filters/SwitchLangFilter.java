package ua.mishkyroff.carget.filters;

import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.SessionAttributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

/**
 * This filter works only for /pages/change_lang/* uri
 * The filter sets default locale by given language and redirects to referer page
 *
 * @author Anton Mishkyroff
 */
public class SwitchLangFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession hs = request.getSession(true);
        String language = servletRequest.getParameter("language");
        if (language != null) {
            hs.setAttribute(SessionAttributes.LOCALE.toString(), language);
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
        }
        String currentPagePath = request.getHeader("referer");
        if (currentPagePath == null) {
            response.sendRedirect(JspPages.INDEX.toString());
        } else {
            response.sendRedirect(currentPagePath);
        }
    }

    @Override
    public void destroy() {

    }
}
