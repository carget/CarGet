package ua.mishkyroff.carget.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This singleton class used to retrieve corresponding {@code Command} object for given uri
 *
 * @author Anton Mishkyroff
 * @see AbstractActionFactory
 */

public class ActionFactoryPost extends AbstractActionFactory {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    private final static ActionFactoryPost INSTANCE = new ActionFactoryPost();

    private ActionFactoryPost() {
        super(POST_COMMAND_BINDING_FILE);
    }

    public static ActionFactoryPost getInstance() {
        return INSTANCE;
    }


}
