package ua.mishkyroff.carget.commands;

import ua.mishkyroff.carget.controllers.IRequestWrapper;

/**
 * A Command for operating requests from users with wrong role for given uri.
 * Always redirects to a error user role page
 *
 * @author Anton Mishkyroff
 */
public class ErrorUserRoleCommand implements Command {
    @Override
    public String execute(IRequestWrapper wrapper) {
        return ERROR_USER_ROLE;
    }
}
