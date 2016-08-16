package ua.mishkyroff.carget.dao.Exceptions;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public class DBException extends Exception {

    public DBException() {
    }

    public DBException(Exception e) {
        super(e);
    }

    public DBException(String message) {
        super(message);
    }
}
