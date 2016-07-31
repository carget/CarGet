package ua.mishkyroff.carget.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This singleton class used to retrieve corresponding {@code Command} object for given uri
 *
 * @author Anton Mishkyroff
 * @see AbstractActionFactory
 */
public class ActionFactoryGet extends AbstractActionFactory{

    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    private final static ActionFactoryGet INSTANCE = new ActionFactoryGet();

    private ActionFactoryGet() {
        super(GET_COMMAND_BINDING_FILE);
    }

    public static ActionFactoryGet getInstance() {
        return INSTANCE;
    }

}
