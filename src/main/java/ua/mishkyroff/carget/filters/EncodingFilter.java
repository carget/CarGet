package ua.mishkyroff.carget.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * This filter sets encoding in request and response to the selected in web.xml file
 *
 * @author Anton Mishkyroff
 */
public class EncodingFilter implements Filter {
    private String enc;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.enc = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        String codeRequest = servletRequest.getCharacterEncoding();
        if (enc != null && !enc.equalsIgnoreCase(codeRequest)) {
            servletRequest.setCharacterEncoding(enc);
            servletResponse.setCharacterEncoding(enc);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        enc = null;
    }
}
