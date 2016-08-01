package ua.mishkyroff.carget.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.dao.Exceptions.DBStructureError;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;


/**
 * Singleton class {@code DBInitializationDAO} used for checking DB structure
 * If some required tables are missing throw exception
 *
 * @author Anton Mishkyroff
 * @throws DBStructureError
 */
public class DBInitializationDAO {
    private final DataSource ds;
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    DBInitializationDAO(DataSource ds) {
        this.ds = ds;
    }

    /**
     * Method checks and compare required and real table names in database
     * And throws DBStructureError if some or all tables are missing
     *
     * @throws DBStructureError - thrown if some or all table are missing in database
     */
    public void initAndCheckDB() throws SQLException, DBStructureError {
        Connection connection = ds.getConnection();
        //check metainf table
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getTables(null, null, "%", null);
        Set<String> realTables = new HashSet<>();
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            realTables.add(tableName);
            LOGGER.debug("DB TABLE NAME=" + tableName);
        }
        Set<String> requiredTables = new HashSet<>();
        requiredTables.add("brands");
        requiredTables.add("cars");
        requiredTables.add("models");
        requiredTables.add("orders");
        requiredTables.add("users");
        if (!realTables.containsAll(requiredTables)) {
            //some tables are missing!
            throw new DBStructureError("Database structure Error!\nInit DB first!\nDB does not" +
                    " have some or all of required tables. Required tables are: " + requiredTables);
        }
    }
}

