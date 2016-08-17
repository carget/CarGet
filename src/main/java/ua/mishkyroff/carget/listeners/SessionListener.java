package ua.mishkyroff.carget.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Class {@code SessionListener} generates CSRF token and store as session attribute each time
 * session was created
 *
 * @author Anton Mishkyroff
 */
public class SessionListener implements HttpSessionListener {
    private static final Logger LOGGER_CONSOLE = LogManager.getLogger("toConsole");

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        //generate CSRF token
        Long token = (long) (Math.random() * Long.MAX_VALUE);
        httpSessionEvent.getSession().setAttribute("csrfToken", token);
        LOGGER_CONSOLE.info("Session created and CSRF Token has been generated = " + token);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        LOGGER_CONSOLE.info("Session destroyed");
    }
}
