package ua.mishkyroff.carget.model.commands.idempotent;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process GET requests with "/guest_register" uri
 * The command just forwards to registration page
 *
 * @author Anton Mishkyroff
 */
public class RegisterCommand implements Command {
    @Override
    public View execute(IRequestWrapper wrapper) {
        return View.GUEST_REGISTER;
    }
}
