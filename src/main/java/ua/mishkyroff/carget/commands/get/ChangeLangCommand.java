package ua.mishkyroff.carget.commands.get;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;

import java.util.Locale;

/**
 * A command for process GET requests with "/change_lang" uri
 * The command switches locale to given request parameter
 * and save current language identifier to current session
 *
 * @author Anton Mishkyroff
 */
public class ChangeLangCommand implements Command {

    private static final Logger CONSOLE = LogManager.getLogger("toConsole");

    @Override
    public String execute(IRequestWrapper wrapper) {
        String language = wrapper.getParameter("language");
        if (language != null) {
            wrapper.setSessionAttribute(SessionAttributes.LOCALE, language);
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
        }
        String currentPagePath = wrapper.getHeader("referer");
        if (currentPagePath == null) {
            return INDEX;
        } else {
            return currentPagePath;
        }
    }
}
