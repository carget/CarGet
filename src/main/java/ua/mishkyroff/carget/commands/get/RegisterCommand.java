package ua.mishkyroff.carget.commands.get;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;

/**
 * A command for process GET requests with "/guest_register" uri
 * The command just forwards to registration page
 *
 * @author Anton Mishkyroff
 */
public class RegisterCommand implements Command {
    @Override
    public JspPages execute(IRequestWrapper wrapper) {
        return JspPages.GUEST_REGISTER;
    }
}
