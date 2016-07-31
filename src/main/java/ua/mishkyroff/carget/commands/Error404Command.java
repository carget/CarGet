package ua.mishkyroff.carget.commands;

import ua.mishkyroff.carget.controllers.IRequestWrapper;

/**
 * A Command for operating requests with not defined uri.
 * Always redirects to a error 404 page
 *
 * @author Anton Mishkyroff
 */
public class Error404Command implements Command {
    @Override
    public String execute(IRequestWrapper wrapper) {
        return ERROR_404;
    }
}
