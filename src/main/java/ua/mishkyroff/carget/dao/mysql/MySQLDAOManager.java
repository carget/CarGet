package ua.mishkyroff.carget.dao.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.dao.*;
import ua.mishkyroff.carget.dao.Exceptions.DBException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * //TODO add comments
 *
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

    public MySQLDAOManager(DataSource ds) {
        this.ds = ds;
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
    public synchronized OrdersDAO getOrdersDAO() {

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
    public synchronized BrandsDAO getBrandsDAO() {

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
    public synchronized CheckDBDAO getInitDBDAO() {
        if (instanceCheckDBDAO == null) {
            return new MySQLCheckDBDAO(connection);
        } else {
            return instanceCheckDBDAO;
        }
    }

    @Override
    public void openConnection() throws DBException {
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Can't get connection ", e);
            throw new DBException(e);
        }

    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close connection ", e);
            }
        }
    }

    @Override
    public void startTransaction() throws DBException {
        openConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Can't start transaction ", e);
            throw new DBException(e);
        }
    }

    @Override
    public void rollbackTransaction() {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("Can't rollback transaction ", e);
            }
        }
    }

    @Override
    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Can't end transaction ", e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public Object openExecuteAndClose(DAOCommand command) {
        try {
            openConnection();
            return command.execute(this);
        } catch (DBException e) {
            LOGGER.error(e);
            return null;
        } finally {
            closeConnection();
        }
    }
}
