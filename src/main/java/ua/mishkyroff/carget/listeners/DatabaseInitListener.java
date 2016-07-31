package ua.mishkyroff.carget.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.dao.DBInitializationDAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

/**
 * Created by U on 24.07.2016.
 */
public class DatabaseInitListener implements ServletContextListener {

    private static final Logger LOGGER_CONSOLE = LogManager.getLogger("toConsole");

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        //init datasource
        //create tables in DB and fill it with default (example) values
        try {
            DBInitializationDAO DBInitializationDAO = DAOFactory.getInstance().getInitDBDAO();
            DBInitializationDAO.initDB();
        } catch (SQLException e) {
            LOGGER_CONSOLE.error("error during DB initialization " + e.getMessage());
        }
        LOGGER_CONSOLE.info("DB initialized!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER_CONSOLE.info("Context destroyed!");
    }
}
