package ua.mishkyroff.carget.commands;

import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;

/**
 * A Command for operating requests with not defined uri.
 * Always redirects to a error 404 page
 *
 * @author Anton Mishkyroff
 */
public class Error404Command implements Command {
    @Override
    public JspPages execute(IRequestWrapper wrapper) {
        return JspPages.ERROR_404;
    }
}
