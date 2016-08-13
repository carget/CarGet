package ua.mishkyroff.carget.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controllers.IRequestWrapper;

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

    //we don't use lazy initialization for this two common used factories
    private static CommandFactory commandFactoryGet = new CommandFactory(GET_COMMAND_BINDING_FILE);
    private static CommandFactory commandFactoryPost = new CommandFactory(POST_COMMAND_BINDING_FILE);

    public static CommandFactory getCommandFactoryGet() {
        return commandFactoryGet;
    }

    public static CommandFactory getCommandFactoryPost() {
        return commandFactoryPost;
    }

    protected HashMap<String, Command> commands = new HashMap<>();

    /**
     * Instantiates Command objects by name from .properties file
     * Creates and fills map with given uri and created Command objects
     *
     * @param propertyFile - file name of .properties file
     */
    private CommandFactory(String propertyFile) {
        //get class names from properties file
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream(propertyFile);
            properties.load(input);
            for (String key : properties.stringPropertyNames()) {
                //instantiate new Command class and put it to the map
                String className = properties.getProperty(key);
                Command newCommand = (Command) Class.forName(className).newInstance();
                commands.put(key, newCommand);
            }
        } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            LOGGER.error("Error during create commands to class binding " + e.getMessage());
        }
    }

    /**
     * Returns a {@code Command} that corresponds the request.
     *
     * @param wrapper - used to retrieve current uri
     * @return {@code Command} object that can process request with corresponding uri
     */
    public Command getCommand(IRequestWrapper wrapper) {
        String pathInfo = wrapper.getPathInfo();
        pathInfo = (pathInfo == null || pathInfo.equals("/")) ? "/index" : pathInfo;
        LOGGER.debug(wrapper.getMethod() + " command from request = " + pathInfo);
        Command command = commands.get(pathInfo.substring(1));
        if (command == null) {
            return commands.get("404");
        } else {
            return command;
        }
    }
}
