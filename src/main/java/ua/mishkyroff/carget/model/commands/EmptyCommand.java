package ua.mishkyroff.carget.model.commands;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.View;

/**
 * A Command for operating requests to the "/" or "/index" uri.
 * Always redirects to a main index page
 *
 * @author Anton Mishkyroff
 */
public class EmptyCommand implements Command {

    @Override
    public View execute(IRequestWrapper wrapper) {
        return View.INDEX;
    }
}
