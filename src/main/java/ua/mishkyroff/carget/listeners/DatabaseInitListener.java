package ua.mishkyroff.carget.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.dao.Exceptions.DBStructureError;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

/**
 * Class {@code DatabaseInitListener} checks DB structure and initialize DataSource object
 * If some required tables are missing throw exception
 *
 * @author Anton Mishkyroff
 */
public class DatabaseInitListener implements ServletContextListener {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");
    private static final Logger LOGGER_FILE = LogManager.getLogger("toFile");

    /**
     * Initialize DataSource and check database for required tables
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        try {
             DAOFactory.getInstance().getInitDBDAO().initAndCheckDB();
        } catch (SQLException e) {
            LOGGER.error("SQL error during DB initialization " + e.getMessage());
        } catch (DBStructureError dbStructureError) {
            LOGGER.error(dbStructureError.getMessage());
        }
        LOGGER.info("DB checked!");
        LOGGER_FILE.info("DB checked!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("Context destroyed!");
    }
}
