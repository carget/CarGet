package ua.mishkyroff.carget.model.commands;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.View;

/**
 * A Command for operating requests from users with wrong role for given uri.
 * Always redirects to a error user role page
 *
 * @author Anton Mishkyroff
 */
public class ErrorUserRoleCommand implements Command {
    @Override
    public View execute(IRequestWrapper wrapper) {
        return View.ERROR_ROLE;
    }
}
