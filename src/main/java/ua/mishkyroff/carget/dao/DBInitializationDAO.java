package ua.mishkyroff.carget.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by U on 21.07.2016.
 */
public class DBInitializationDAO {
    private final DataSource ds;
    private final static ResourceBundle resBundle = ResourceBundle.getBundle("sql_init");
    private static final Logger LOGGER_SQL = LogManager.getLogger("toConsole");

    DBInitializationDAO(DataSource ds) {
        this.ds = ds;
    }

    public void initDB() throws SQLException {
        Connection connection = ds.getConnection();
        //check metainf table, if not exist - init DB with default tables and values
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getTables(null, null, "%", null);
        //if database hasn't any tables, we create default structure of DB with default data
        if (!rs.next()) {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(resBundle.getString("CREATE_BRANDS"));
//            statement.executeUpdate(resBundle.getString("CREATE_CARS"));
//            statement.executeUpdate(resBundle.getString("CREATE_MODELS"));
//            statement.executeUpdate(resBundle.getString("CREATE_ORDERS"));
//            statement.executeUpdate(resBundle.getString("CREATE_USERS"));
//            statement.executeUpdate(resBundle.getString("INIT_BRANDS"));
//            statement.executeUpdate(resBundle.getString("INIT_USERS"));
//            statement.executeUpdate(resBundle.getString("INSERT_CARS"));
        } else {
            do {
                LOGGER_SQL.debug("TABLE NAME=" + rs.getString("TABLE_NAME"));
            } while (rs.next());
        }


    }
}

