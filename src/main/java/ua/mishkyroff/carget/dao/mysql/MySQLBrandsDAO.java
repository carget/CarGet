package ua.mishkyroff.carget.dao.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.dao.BrandsDAO;
import ua.mishkyroff.carget.entities.Brand;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class {@code BrandsDAO} used for CRUD and other useful DB operations with Brand entity
 *
 * @author Anton Mishkyroff
 */
public class MySQLBrandsDAO implements BrandsDAO {
    private final DataSource ds;
    private static final Logger LOGGER_SQL = LogManager.getLogger("toConsole");
    private static final ResourceBundle bundle = ResourceBundle.getBundle("sql_statements");

    public MySQLBrandsDAO(DataSource ds) {
        this.ds = ds;
    }

    @Override public List<Brand> getAllBrands() {

        List<Brand> brands = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(bundle.getString("GET_ALL_BRANDS"));
            while (rs.next()) {
                Brand brand = new Brand(
                        rs.getInt("brand_id"),
                        rs.getString("brand_name"),
                        rs.getString("brand_full_name")
                );
                brands.add(brand);
            }
        } catch (SQLException e) {
            LOGGER_SQL.error("get all brands SQL error " + e);
            return null;
        }
        return brands;
    }

}
