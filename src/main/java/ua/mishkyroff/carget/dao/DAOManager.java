package ua.mishkyroff.carget.dao;

import ua.mishkyroff.carget.dao.Exceptions.DAOManagerException;
import ua.mishkyroff.carget.dao.mysql.MySQLDAOManager;

/**
 * An interface for very useful object that manages connection, can start/stop connection
 * (transaction). Can execute command in transaction and connection scope
 * Also provides interface for retrieving all DAO objects
 *
 * @see MySQLDAOManager
 *
 * @author Anton Mishkyroff
 */
public interface DAOManager {

    UsersDAO getUsersDAO();

    CarsDAO getCarsDAO();

    OrdersDAO getOrdersDAO();

    BrandsDAO getBrandsDAO();

    CheckDBDAO getInitDBDAO();

    void openConnection() throws DAOManagerException;

    void closeConnection();

    void startTransaction() throws DAOManagerException;

    void rollbackTransaction();

    void commitTransaction();

    Object openExecuteAndClose(DAOCommand command);

    Object transaction(DAOCommand command);
}
