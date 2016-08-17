package ua.mishkyroff.carget.dao;

import ua.mishkyroff.carget.dao.Exceptions.DBStructureError;

import java.sql.SQLException;

/**
 * Interface for checking database structure (required tables)
 *
 * @author Anton Mishkyroff
 */
public interface CheckDBDAO {
    void initAndCheckDB() throws SQLException, DBStructureError;
}
