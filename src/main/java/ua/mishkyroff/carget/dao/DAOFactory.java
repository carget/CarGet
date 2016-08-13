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

    private static final DAOFactory INSTANCE = new DAOFactory();
    private DataSource ds;

    private volatile UsersDAO instanceUsersDAO;
    private volatile CarsDAO instanceCarsDAO;
    private volatile OrdersDAO instanceOrdersDAO;
    private volatile BrandsDAO instanceBrandsDAO;
    private volatile CheckDBDAO instanceCheckDBDAO;

    private DAOFactory() {
        try {
            InitialContext initialContext = new InitialContext();
            ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/carget");
        } catch (NamingException e) {
            LOGGER.error("Error during DataSource initialization " + e.getMessage());
        }
    }

    public static DAOFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Method uses lazy initialization and double checked locking & volatile to improve performance
     *
     * @return UsersDAO singleton object
     */
    public UsersDAO getUsersDAO() {

        UsersDAO localUsersDAO = instanceUsersDAO;
        if (localUsersDAO == null) {
            synchronized (UsersDAO.class) {
                localUsersDAO = instanceUsersDAO;
                if (localUsersDAO == null) {
                    instanceUsersDAO = localUsersDAO = new UsersDAO(ds);
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
    public synchronized CarsDAO getCarsDAO() {

        CarsDAO localCarsDAO = instanceCarsDAO;
        if (localCarsDAO == null) {
            synchronized (CarsDAO.class) {
                localCarsDAO = instanceCarsDAO;
                if (localCarsDAO == null) {
                    instanceCarsDAO = localCarsDAO = new CarsDAO(ds);
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
    public synchronized OrdersDAO getOrdersDAO() {

        OrdersDAO localOrdersDAO = instanceOrdersDAO;
        if (localOrdersDAO == null) {
            synchronized (OrdersDAO.class) {
                localOrdersDAO = instanceOrdersDAO;
                if (localOrdersDAO == null) {
                    instanceOrdersDAO = localOrdersDAO = new OrdersDAO(ds);
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
    public synchronized BrandsDAO getBrandsDAO() {

        BrandsDAO localBrandsDAO = instanceBrandsDAO;
        if (localBrandsDAO == null) {
            synchronized (BrandsDAO.class) {
                localBrandsDAO = instanceBrandsDAO;
                if (localBrandsDAO == null) {
                    instanceBrandsDAO = localBrandsDAO = new BrandsDAO(ds);
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
    public synchronized CheckDBDAO getInitDBDAO() {
        CheckDBDAO localCheckDBDAO = instanceCheckDBDAO;
        if (localCheckDBDAO == null) {
            synchronized (CheckDBDAO.class) {
                localCheckDBDAO = instanceCheckDBDAO;
                if (localCheckDBDAO == null) {
                    instanceCheckDBDAO = localCheckDBDAO = new CheckDBDAO(ds);
                }
            }
        }
        return localCheckDBDAO;
    }
}
