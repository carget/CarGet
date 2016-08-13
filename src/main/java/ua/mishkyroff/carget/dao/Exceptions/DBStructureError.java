package ua.mishkyroff.carget.dao.Exceptions;

import ua.mishkyroff.carget.dao.CheckDBDAO;

/**
 * this exception throws when DB structure is invalid
 *
 * @see CheckDBDAO
 */
public class DBStructureError extends Exception{
    public DBStructureError() {}

    public DBStructureError(String message) {
        super(message);
    }
}
