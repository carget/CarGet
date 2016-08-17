package ua.mishkyroff.carget.dao.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.dao.*;
import ua.mishkyroff.carget.dao.Exceptions.DAOManagerException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class manages connections from datasource and create DAO objects with required connection.
 * It also provides methods for starting and execute transactions.
 * Can execute command in transaction or connection scope with handling all exception. It makes
 * client code much cleaner.
 *
 * @see DAOCommand
 * @author Anton Mishkyroff
 */
public class MySQLDAOManager implements DAOManager {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    private UsersDAO instanceUsersDAO;
    private CarsDAO instanceCarsDAO;
    private OrdersDAO instanceOrdersDAO;
    private BrandsDAO instanceBrandsDAO;
    private CheckDBDAO instanceCheckDBDAO;

    private DataSource ds;
    private Connection connection;
    private boolean autoCommit;

    public MySQLDAOManager(DataSource ds) {
        this.ds = ds;
        autoCommit = true; //default value
    }

    /**
     * Method uses lazy initialization
     *
     * @return UsersDAO singleton object
     */
    @Override
    public UsersDAO getUsersDAO() {
        if (instanceUsersDAO == null) {
            return new MySQLUsersDAO(connection);
        } else {
            return instanceUsersDAO;
        }
    }

    /**
     * Method uses lazy initialization
     *
     * @return CarsDAO singleton object
     */
    @Override
    public CarsDAO getCarsDAO() {
        if (instanceCarsDAO == null) {
            return new MySQLCarsDAO(connection);
        } else {
            return instanceCarsDAO;
        }
    }

    /**
     * Method uses lazy initialization
     *
     * @return OrdersDAO singleton object
     */
    @Override
    public OrdersDAO getOrdersDAO() {

        if (instanceOrdersDAO == null) {
            return new MySQLOrdersDAO(connection);
        } else {
            return instanceOrdersDAO;
        }
    }

    /**
     * Method uses lazy initialization
     *
     * @return BrandsDAO singleton object
     */
    @Override
    public BrandsDAO getBrandsDAO() {

        if (instanceBrandsDAO == null) {
            return new MySQLBrandsDAO(connection);
        } else {
            return instanceBrandsDAO;
        }
    }

    /**
     * Method uses lazy initialization
     *
     * @return CheckDBDAO singleton object
     */
    @Override
    public CheckDBDAO getInitDBDAO() {
        if (instanceCheckDBDAO == null) {
            return new MySQLCheckDBDAO(connection);
        } else {
            return instanceCheckDBDAO;
        }
    }

    /**
     * Opens connection and saves its autocommit value
     * This value will be restored before closing connection
     *
     * @see #closeConnection()
     * @throws DAOManagerException
     */
    @Override
    public void openConnection() throws DAOManagerException {
        try {
            connection = ds.getConnection();
            //save autocommit value
            this.autoCommit = connection.getAutoCommit();
        } catch (SQLException e) {
            LOGGER.error("Can't get connection ", e);
            throw new DAOManagerException(e);
        }

    }

    /**
     * Restore autocommit value and closes connection
     *
     * @see #openConnection()
     */
    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                //restore autocommit value
                connection.setAutoCommit(autoCommit);
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close connection ", e);
            }
        }
    }

    /**
     * Mark start of transaction
     * In client code should be used this method and two others together:
     * @see #commitTransaction()
     * @see #rollbackTransaction()
     *
     * @throws DAOManagerException
     */
    @Override
    public void startTransaction() throws DAOManagerException {
        openConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Can't start transaction ", e);
            throw new DAOManagerException(e);
        }
    }

    /**
     * Rollback current started transaction.
     * Should be used together with:
     * @see #startTransaction()
     * @see #commitTransaction() ()
     */
    @Override
    public void rollbackTransaction() {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("Can't rollback transaction ", e);
            } finally {
                closeConnection();
            }
        }
    }

    /**
     * Rollback current started transaction.
     * Should be used together with:
     * @see #startTransaction()
     * @see #rollbackTransaction()
     */
    @Override
    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Can't commit transaction ", e);
        } finally {
            closeConnection();
        }
    }

    /**
     * This method open connection and execute passed command.
     * Also handles all exception and make client code cleaner
     *
     * @param command - DAOCommand object witch execute() method will bew executed with new
     *                opened connection
     * @return - object that represents result of command
     */
    @Override
    public Object openExecuteAndClose(DAOCommand command) {
        try {
            openConnection();
            return command.execute(this);
        } catch (DAOManagerException e) {
            LOGGER.error(e);
            return null;
        } finally {
            closeConnection();
        }
    }

    /**
     * The method execute argument command as a transaction.
     * Also handles all exception and make client code cleaner
     *
     * @param command - DAOCommand object witch execute() method will bew executed with new
     *                transaction
     * @return - object that represents result of command
     */
    @Override
    public Object transaction(DAOCommand command) {
        Object result = null;
        try {
            openConnection();
            startTransaction();
            result = command.execute(this);
            commitTransaction();
        } catch (DAOManagerException e) {
            LOGGER.error(e);
            rollbackTransaction();
        }
        return result;
    }
}
