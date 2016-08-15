package ua.mishkyroff.carget.model.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controller.IRequestWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * This class used to retrieve {@code Command} objects for given uri
 * URI and corresponding class name are taken from .properties file
 *
 * @author Anton Mishkyroff
 */
public class CommandFactory {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    private static final String GET_COMMAND_BINDING_FILE = "commands_binding_get.properties";
    private static final String POST_COMMAND_BINDING_FILE = "commands_binding_post.properties";

    //Properties object to store commands' class names
    private Properties properties;

    //we don't use lazy initialization for this two common used factories
    private static final CommandFactory COMMAND_FACTORY_GET = new CommandFactory(GET_COMMAND_BINDING_FILE);
    private static final CommandFactory COMMAND_FACTORY_POST = new CommandFactory(POST_COMMAND_BINDING_FILE);

    public static CommandFactory getCommandFactoryGet() {
        return COMMAND_FACTORY_GET;
    }

    public static CommandFactory getCommandFactoryPost() {
        return COMMAND_FACTORY_POST;
    }

    protected HashMap<String, Command> commands = new HashMap<>();

    /**
     * Load Commands' class names from .properties file
     *
     * @param propertyFile - file name of .properties file
     */
    private CommandFactory(String propertyFile) {

        properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream input = classLoader.getResourceAsStream(propertyFile)) {
            properties.load(input);
        } catch (IOException e) {
            LOGGER.error("Error during reading .properties file \"" + propertyFile + "\" " + e
                    .getMessage());
        }
    }

    /**
     * Returns a {@code Command} object corresponding to the request uri.
     * The method creates Command object and saves it to the map for future usage only
     * if command object hasn't created yet.
     *
     * @param wrapper - used to retrieve current uri
     * @return {@code Command} object that can process request with corresponding uri
     */
    public Command getCommand(IRequestWrapper wrapper) {

        String uri = wrapper.getPathInfo();
        uri = (uri == null || uri.equals("/")) ? "/index" : uri;
        LOGGER.debug(wrapper.getMethod() + " command from request = " + uri);
        Command command = commands.get(uri);
        if (command != null) {
            return command;
        }
        //try to get command className from property
        String className = properties.getProperty(uri);
        if (className != null) {
            Command newCommand = null;
            LOGGER.debug("Instantiate new Command class for name = " + className);
            //instantiate new Command class and put it to the map
            try {
                newCommand = (Command) Class.forName(className).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                LOGGER.error("Error during create command from property " + e.getMessage());
            }
            commands.put(uri, newCommand);
            return newCommand;
        } else {
            Command errorCommand = commands.get("/404");
            if (errorCommand == null) {
                errorCommand = new Error404Command();
                commands.put("/404", errorCommand);
            }
            return errorCommand;
        }
    }
}
