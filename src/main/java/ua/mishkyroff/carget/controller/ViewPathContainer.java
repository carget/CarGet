package ua.mishkyroff.carget.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public class ViewPathContainer {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    private static final String PROPERTY_FILE = "view_path.properties";

    private Map<String, String> viewPathMap;

    public ViewPathContainer() {

        viewPathMap = new HashMap<>();
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream input = classLoader.getResourceAsStream(PROPERTY_FILE)) {
            properties.load(input);
            for (String uri : properties.stringPropertyNames()) {
                viewPathMap.put(uri, properties.getProperty(uri));
            }
        } catch (IOException e) {
            LOGGER.error("Error during create view to jsp path binding " + e.getMessage());
        }
    }

    public String getPath(String uri) {
        return viewPathMap.get(uri);
    }
}
