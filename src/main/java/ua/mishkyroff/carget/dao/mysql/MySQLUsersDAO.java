package ua.mishkyroff.carget.dao.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.dao.UsersDAO;
import ua.mishkyroff.carget.entities.User;
import ua.mishkyroff.carget.entities.UserRole;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Class {@code UsersDAO} used for CRUD and other useful DB operations with User entity
 *
 * @author Anton Mishkyroff
 */
public class MySQLUsersDAO implements UsersDAO {
    private DataSource ds;
    private static final Logger LOGGER = LogManager.getLogger("toConsole");
    private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("sql_statements");

    public MySQLUsersDAO(DataSource ds) {
        this.ds = ds;
    }

    @Override public UserRole getUserRole(User user) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("USER_ROLE"));
            ps.setString(1, user.getEmail());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getBoolean("is_admin")) {
                    return UserRole.ADMIN;
                } else {
                    return UserRole.USER;
                }
            } else {
                return UserRole.GUEST;
            }
        } catch (SQLException e) {
            LOGGER.error("check user's role SQL error " + e);
            return UserRole.GUEST;
        }
    }

    @Override public boolean add(User user) {
        if (getUserByEmail(user.getEmail()) != null) {
            //we have such user in DB
            return false;
        }
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("INSERT_USER"));
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassport());
            ps.setString(5, (user.getAdmin() ? "1" : "0"));
            ps.setString(6, user.getPassword());
            LOGGER.debug(ps);
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("ADD user SQL error " + e);
            return false;
        }
        return true;
    }

    @Override public User getUserByEmail(String email) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("GET_USER_BY_EMAIL"));
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return getUserFromResultSet(rs);
        } catch (SQLException e) {
            LOGGER.error("GET USER by EMAIL SQL error, " + e);
            return null;
        }
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User(
                rs.getInt("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("passport"),
                rs.getString("email"),
                rs.getBoolean("is_admin"),
                rs.getString("password")
        );
        return user;
    }

    @Override public User getUserById(Integer id) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("GET_USER_BY_ID"));
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return getUserFromResultSet(rs);
        } catch (SQLException e) {
            LOGGER.error("GET USER by ID SQL error, " + e);
            return null;
        }
    }
}
