package ua.mishkyroff.carget.dao;

import ua.mishkyroff.carget.dao.mysql.MySQLDAOFactory;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public abstract class AbstractDAOFactory {

    public static AbstractDAOFactory getDAOFactory(String factoryType){
        switch (factoryType) {
            case "MySQL":
                return MySQLDAOFactory.getInstance();
            default:
                return null;
        }
    }

    public abstract UsersDAO getUsersDAO();

    public abstract CarsDAO getCarsDAO();

    public abstract OrdersDAO getOrdersDAO();

    public abstract BrandsDAO getBrandsDAO();

    public abstract CheckDBDAO getInitDBDAO();
}
