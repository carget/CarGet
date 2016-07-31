package ua.mishkyroff.carget.dao;

import org.apache.logging.log4j.*;
import ua.mishkyroff.carget.entities.User;
import ua.mishkyroff.carget.entities.UserRole;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by U on 16.07.2016.
 */
public class UsersDAO {
    private DataSource ds;
    private static final Logger LOGGER = LogManager.getLogger("toConsole");
    private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("sql_statements");

    UsersDAO(DataSource ds) {
        this.ds = ds;
    }

    public UserRole getUserRole(User user) {
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

    public boolean add(User user) {
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

    public User getUserByEmail(String email) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("GET_USER_BY_EMAIL"));
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            User user = new User();
            if (!rs.next()) {
                return null;
            }
            user.setId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPassport(rs.getString("passport"));
            user.setAdmin(rs.getBoolean("is_admin"));
            user.setPassword(rs.getString("password"));
            return user;
        } catch (SQLException e) {
            LOGGER.error("GET USER by EMAIL SQL error, " + e);
            return null;
        }
    }

    public User getUserById(Integer id) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("GET_USER_BY_ID"));
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();
            User user = new User();
            if (!rs.next()) {
                return null;
            }
            user.setId(rs.getInt("user_id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPassport(rs.getString("passport"));
            user.setAdmin(rs.getBoolean("is_admin"));
            user.setPassword(rs.getString("password"));
            return user;
        } catch (SQLException e) {
            LOGGER.error("GET USER by ID SQL error, " + e);
            return null;
        }
    }
}
