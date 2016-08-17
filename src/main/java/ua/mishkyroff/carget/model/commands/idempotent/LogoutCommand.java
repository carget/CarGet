package ua.mishkyroff.carget.model.commands.idempotent;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process GET requests with "/logout" uri
 * The command invalidates user's session and forwards to index page
 *
 * @author Anton Mishkyroff
 */
public class LogoutCommand implements Command {
    @Override
    public View execute(IRequestWrapper wrapper) {
        wrapper.invalidateSession();
        return View.INDEX;
    }
}
