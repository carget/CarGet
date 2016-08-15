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

    private volatile UsersDAO instanceUsersDAO;
    private volatile CarsDAO instanceCarsDAO;
    private volatile OrdersDAO instanceOrdersDAO;
    private volatile BrandsDAO instanceBrandsDAO;
    private volatile CheckDBDAO instanceCheckDBDAO;

    private MySQLDAOFactory() {
        try {
            InitialContext initialContext = new InitialContext();
            ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/carget");
        } catch (NamingException e) {
            LOGGER.error("Error during DataSource initialization " + e.getMessage());
        }
    }

    public static MySQLDAOFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Method uses lazy initialization and double checked locking & volatile to improve performance
     *
     * @return UsersDAO singleton object
     */
    @Override
    public UsersDAO getUsersDAO() {

        UsersDAO localUsersDAO = instanceUsersDAO;
        if (localUsersDAO == null) {
            synchronized (UsersDAO.class) {
                localUsersDAO = instanceUsersDAO;
                if (localUsersDAO == null) {
                    instanceUsersDAO = localUsersDAO = new MySQLUsersDAO(ds);
                }
            }
        }
        return localUsersDAO;
    }

    /**
     * Method uses lazy initialization and double checked locking & volatile to improve performance
     *
     * @return CarsDAO singleton object
     */
    @Override
    public synchronized CarsDAO getCarsDAO() {

        CarsDAO localCarsDAO = instanceCarsDAO;
        if (localCarsDAO == null) {
            synchronized (CarsDAO.class) {
                localCarsDAO = instanceCarsDAO;
                if (localCarsDAO == null) {
                    instanceCarsDAO = localCarsDAO = new MySQLCarsDAO(ds);
                }
            }
        }
        return localCarsDAO;
    }

    /**
     * Method uses lazy initialization and double checked locking & volatile to improve performance
     *
     * @return OrdersDAO singleton object
     */
    @Override
    public synchronized OrdersDAO getOrdersDAO() {

        OrdersDAO localOrdersDAO = instanceOrdersDAO;
        if (localOrdersDAO == null) {
            synchronized (OrdersDAO.class) {
                localOrdersDAO = instanceOrdersDAO;
                if (localOrdersDAO == null) {
                    instanceOrdersDAO = localOrdersDAO = new MySQLOrdersDAO(ds);
                }
            }
        }
        return localOrdersDAO;
    }

    /**
     * Method uses lazy initialization and double checked locking & volatile to improve performance
     *
     * @return BrandsDAO singleton object
     */
    @Override
    public synchronized BrandsDAO getBrandsDAO() {

        BrandsDAO localBrandsDAO = instanceBrandsDAO;
        if (localBrandsDAO == null) {
            synchronized (BrandsDAO.class) {
                localBrandsDAO = instanceBrandsDAO;
                if (localBrandsDAO == null) {
                    instanceBrandsDAO = localBrandsDAO = new MySQLBrandsDAO(ds);
                }
            }
        }
        return localBrandsDAO;
    }

    /**
     * Method uses lazy initialization and double checked locking & volatile to improve performance
     *
     * @return CheckDBDAO singleton object
     */
    @Override
    public synchronized CheckDBDAO getInitDBDAO() {
        CheckDBDAO localCheckDBDAO = instanceCheckDBDAO;
        if (localCheckDBDAO == null) {
            synchronized (CheckDBDAO.class) {
                localCheckDBDAO = instanceCheckDBDAO;
                if (localCheckDBDAO == null) {
                    instanceCheckDBDAO = localCheckDBDAO = new MySQLCheckDBDAO(ds);
                }
            }
        }
        return localCheckDBDAO;
    }
}
