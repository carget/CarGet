package ua.mishkyroff.carget.dao;

import ua.mishkyroff.carget.dao.Exceptions.DBException;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public interface DAOManager {



    UsersDAO getUsersDAO();

    CarsDAO getCarsDAO();

    OrdersDAO getOrdersDAO();

    BrandsDAO getBrandsDAO();

    CheckDBDAO getInitDBDAO();

    void openConnection() throws DBException;

    void closeConnection();

    void startTransaction() throws DBException;

    void rollbackTransaction();

    void commitTransaction();

    Object openExecuteAndClose(DAOCommand command);
}
