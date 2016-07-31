package ua.mishkyroff.carget.commands;

import ua.mishkyroff.carget.controllers.IRequestWrapper;

/**
 * A Command for operating requests to the "/" or "/index" uri.
 * Always redirects to a main index page
 *
 * @author Anton Mishkyroff
 */
public class EmptyCommand implements Command {

    @Override
    public String execute(IRequestWrapper wrapper) {
        return INDEX;
    }
}
