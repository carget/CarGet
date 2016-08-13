package ua.mishkyroff.carget.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.commands.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code MainServlet} class extends {@code HttpServlet} class responsible
 * for receiving all valid http requests.
 * It retrieves corresponding Command object for given request and execute the Command.
 * Then it does redirect for all POST requests to avoid "double submit problem"
 * and forwards or redirects for GET requests
 *
 * @author Anton Mishkyroff
 * @see #doGet method
 * @see #doPost method
 */

public class MainServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    /**
     * This method process all http post requests and always does redirect
     * to avoid "double submit problem"
     * This behaviour also known as PRG (Post/Redirect/Get) pattern
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        IRequestWrapper wrapper = new RequestWrapper(request);
        Command command = CommandFactory.getCommandFactoryPost().getCommand(wrapper);
        JspPages view = command.execute(wrapper);
        try {
            LOGGER.debug("-----POST----- REDIRECT to " + view);
            response.sendRedirect(view.getView());
        } catch (IOException e) {
            LOGGER.error("Error during processing POST request " + e.getMessage());
        }
    }

    /**
     * This method process all http get requests and does redirect if uri has been changed
     * or forward if uri has not been changed
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        IRequestWrapper wrapper = new RequestWrapper(request);
        Command command = CommandFactory.getCommandFactoryGet().getCommand(wrapper);
        JspPages view = command.execute(wrapper);
        String pathInfo = wrapper.getPathInfo();
        pathInfo = (pathInfo == null || pathInfo.equals("/")) ? "/index" : pathInfo;
        try {
            if (view.getView().toLowerCase().equals(pathInfo.substring(1))) {
                String forwardPath = view.getPath();
                LOGGER.debug("-----GET----- FORWARD to " + forwardPath);
                request.getRequestDispatcher(forwardPath).forward(request, response);
            } else {
                LOGGER.debug("-----GET----- REDIRECT to " + view);
                response.sendRedirect(view.getView());
            }
        } catch (ServletException | IOException e) {
            LOGGER.error("Error during processing GET request " + e.getMessage());
        }
    }
}
