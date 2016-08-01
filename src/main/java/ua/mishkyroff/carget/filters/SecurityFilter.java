package ua.mishkyroff.carget.filters;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.entities.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Security filter checks CSRF token for all POST requests.
 * Redirects to index if CSRF token does not match in request and current session.
 * Also checks user role and corresponding uri.
 * Set error message in session and redirect to error page
 * if current user role does not match to requested uri.
 *
 * @author Anton Mishkyroff
 */
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession hs = request.getSession(true);

        //check CSRF token only for POST requests
        if (request.getMethod().toLowerCase().equals("post")) {
            Long token = Long.valueOf(request.getParameter("token"));
            if (!token.equals(hs.getAttribute("csrfToken"))) {
                //security error
                response.sendRedirect(Command.INDEX);
                hs.invalidate();
                return;
            }
        }

        UserRole userRole = (UserRole) hs.getAttribute(SessionAttributes.USER_ROLE.toString());

        //TODO goto 404 if not allowed pathInfo()????
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            if (userRole != UserRole.ADMIN && pathInfo.startsWith("/admin")) {
                hs.setAttribute("error" , Command.ERROR_YOU_MUST_BE_ADMIN);
                response.sendRedirect(Command.ERROR_USER_ROLE);
                return;
            }
            if (userRole != UserRole.USER && pathInfo.startsWith("/user")) {
                hs.setAttribute("error" , Command.ERROR_YOU_MUST_BE_USER);
                response.sendRedirect(Command.ERROR_USER_ROLE);
                return;
            }
            if (userRole != UserRole.GUEST && pathInfo.startsWith("/guest")) {
                hs.setAttribute("error" , Command.ERROR_YOU_MUST_BE_GUEST);
                response.sendRedirect(Command.ERROR_USER_ROLE);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
