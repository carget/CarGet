package ua.mishkyroff.carget.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *  Singletone class {@code JspPathFactory} used for retrieve path of .jsp file for given uri
 *
 * @author Anton Mishkyroff
 */
public class JspPathFactory {
    private static final String JSP_PATH_FILE = "jsp_path.properties";
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    private static final JspPathFactory INSTANCE = new JspPathFactory();

    public static JspPathFactory getInstance() {
        return INSTANCE;
    }

    private Map<String, String> paths;

    /**
     * Creates a map from .properties file to store
     * uri as a key and path of .jsp file as a value
     */
    private JspPathFactory() {
        paths = new HashMap<>();
        //get views' names from properties file
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(JSP_PATH_FILE));
            for (String view : properties.stringPropertyNames()) {
                String path = properties.getProperty(view);
                paths.put(view, path);
            }
        } catch (IOException e) {
            LOGGER.error("Error during create view to jsp-path binding " + e.getMessage());
        }
    }

    /**
     * Retrieves .jsp file path from given uri
     *
     * @param uri - given uri
     * @return - string represents path of corresponding .jsp file
     */
    public String getJspPath(String uri) {
        return paths.get(uri);
    }
}
