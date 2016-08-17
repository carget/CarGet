package ua.mishkyroff.carget.dao.Exceptions;

/**
 * Thrown if we have exception in DAO manager object
 *
 * @author Anton Mishkyroff
 */
public class DAOManagerException extends Exception {

    public DAOManagerException() {
    }

    public DAOManagerException(Exception e) {
        super(e);
    }

    public DAOManagerException(String message) {
        super(message);
    }
}
