package ua.mishkyroff.carget.dao;

import ua.mishkyroff.carget.dao.mysql.MySQLDAOFactory;

/**
 * Factory creates concrete DAOFactory
 * Also provides interface for DAOManager object creation
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

    public abstract DAOManager getDAOManager();
}
