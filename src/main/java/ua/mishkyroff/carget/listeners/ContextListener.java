package ua.mishkyroff.carget.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.dao.Exceptions.DAOManagerException;
import ua.mishkyroff.carget.dao.Exceptions.DBStructureError;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

/**
 * Class {@code ContextListener} checks DB structure and initialize DataSource object
 * If some required tables are missing throw exception
 * It also saves MySQLDAOFactory instance to ServletContext scope
 *
 * @author Anton Mishkyroff
 */
public class ContextListener implements ServletContextListener {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");
    private static final Logger LOGGER_FILE = LogManager.getLogger("toFile");

    /**
     * Initialize DataSource and check database for required tables
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory("MySQL");
        DAOManager daoManager = daoFactory.getDAOManager();
        try {
            daoManager.openConnection();
            daoManager.getInitDBDAO().initAndCheckDB();
            servletContextEvent.getServletContext().setAttribute("DAOFactory", daoFactory);
        } catch (SQLException e) {
            LOGGER.fatal("SQL error during DB initialization " + e.getMessage());
        } catch (DBStructureError dbStructureError) {
            LOGGER.fatal(dbStructureError.getMessage());
        } catch (DAOManagerException e) {
            LOGGER.fatal(e.getMessage());
        } finally {
            daoManager.closeConnection();
        }
        LOGGER.info("DB checked!");
        LOGGER_FILE.info("DB checked!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("Context destroyed!");
    }
}
