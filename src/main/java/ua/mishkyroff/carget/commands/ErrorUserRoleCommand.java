package ua.mishkyroff.carget.commands;

import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;

/**
 * A Command for operating requests from users with wrong role for given uri.
 * Always redirects to a error user role page
 *
 * @author Anton Mishkyroff
 */
public class ErrorUserRoleCommand implements Command {
    @Override
    public JspPages execute(IRequestWrapper wrapper) {
        return JspPages.ERROR_ROLE;
    }
}
