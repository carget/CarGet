package ua.mishkyroff.carget.model.commands;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.View;

/**
 * A Command for operating requests with not defined uri.
 * Always redirects to a error 404 page
 *
 * @author Anton Mishkyroff
 */
public class Error404Command implements Command {
    @Override
    public View execute(IRequestWrapper wrapper) {
        return View.ERROR_404;
    }
}
