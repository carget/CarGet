package ua.mishkyroff.carget.dao;

/**
 * Class represents command object that can be passed to transaction or could be executed on
 * single connection
 *
 * @see DAOManager
 * @see DAOManager#transaction(DAOCommand)
 * @see DAOManager#openExecuteAndClose(DAOCommand)
 *
 * @author Anton Mishkyroff
 */
public interface DAOCommand {
    Object
    execute(DAOManager daoManager);
}
