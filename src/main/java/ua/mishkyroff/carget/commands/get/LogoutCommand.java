package ua.mishkyroff.carget.commands.get;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;

/**
 * A command for process GET requests with "/logout" uri
 * The command invalidates user's session and forwards to index page
 *
 * @author Anton Mishkyroff
 */
public class LogoutCommand implements Command {
    @Override
    public JspPages execute(IRequestWrapper wrapper) {
        wrapper.invalidateSession();
        return JspPages.INDEX;
    }
}
