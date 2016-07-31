package ua.mishkyroff.carget.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Singleton class {@code DAOFactory} creates and encapsulates all DAO objects
 * All DAO objects are also singletons
 *
 * @author Anton Mishkyroff
 * @see BrandsDAO
 * @see UsersDAO
 * @see OrdersDAO
 * @see CarsDAO
 */
public class DAOFactory {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    private static final DAOFactory instance = new DAOFactory();
    private DataSource ds;

    private DBInitializationDAO instanceDBInitializationDAO;
    private UsersDAO instanceUsersDAO;
    private CarsDAO instanceCarsDAO;
    private OrdersDAO instanceOrdersDAO;
    private BrandsDAO instanceBrandsDAO;

    private DAOFactory() {
        try {
            InitialContext initialContext = new InitialContext();
            ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/carget");
        } catch (NamingException e) {
            LOGGER.error("Error during DataSource initialization " + e.getMessage());
        }
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public synchronized UsersDAO getUsersDAO() {
        if (instanceUsersDAO == null) {
            instanceUsersDAO = new UsersDAO(ds);
        }
        return instanceUsersDAO;
    }

    public synchronized CarsDAO getCarsDAO() {
        if (instanceCarsDAO == null) {
            instanceCarsDAO = new CarsDAO(ds);
        }
        return instanceCarsDAO;
    }

    public synchronized OrdersDAO getOrdersDAO() {
        if (instanceOrdersDAO == null) {
            instanceOrdersDAO = new OrdersDAO(ds);
        }
        return instanceOrdersDAO;
    }

    public synchronized BrandsDAO getBrandsDAO() {
        if (instanceBrandsDAO == null) {
            instanceBrandsDAO = new BrandsDAO(ds);
        }
        return instanceBrandsDAO;
    }
    public synchronized DBInitializationDAO getInitDBDAO() {
        if (instanceDBInitializationDAO == null) {
            instanceDBInitializationDAO = new DBInitializationDAO(ds);
        }
        return instanceDBInitializationDAO;
    }
}
