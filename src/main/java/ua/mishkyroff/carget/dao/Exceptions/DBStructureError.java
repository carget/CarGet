package ua.mishkyroff.carget.dao.Exceptions;

/**
 * this exception throws when DB structure is invalid
 *
 * @see ua.mishkyroff.carget.dao.DBInitializationDAO
 */
public class DBStructureError extends Exception{
    public DBStructureError() {
    }

    public DBStructureError(String message) {
        super(message);
    }
}
