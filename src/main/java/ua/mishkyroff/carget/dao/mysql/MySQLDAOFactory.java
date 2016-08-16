package ua.mishkyroff.carget.dao.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.dao.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Singleton class {@code MySQLDAOFactory} creates and encapsulates all DAO objects
 * All DAO objects are also singletons
 *
 * @author Anton Mishkyroff
 * @see BrandsDAO
 * @see UsersDAO
 * @see OrdersDAO
 * @see CarsDAO
 */
public class MySQLDAOFactory extends AbstractDAOFactory {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    private static final MySQLDAOFactory INSTANCE = new MySQLDAOFactory();
    private DataSource ds;

    private MySQLDAOFactory() {
        try {
            InitialContext initialContext = new InitialContext();
            ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/carget");
        } catch (NamingException e) {
            LOGGER.fatal("Error during DataSource initialization " + e.getMessage());
        }
    }

    public static MySQLDAOFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public DAOManager getDAOManager() {
        return new MySQLDAOManager(ds);
    }
}
