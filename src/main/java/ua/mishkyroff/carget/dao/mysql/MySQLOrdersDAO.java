package ua.mishkyroff.carget.dao.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.dao.OrdersDAO;
import ua.mishkyroff.carget.entities.*;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class {@code OrdersDAO} used for CRUD and other useful DB operations with Order entity
 *
 * @author Anton Mishkyroff
 */
public class MySQLOrdersDAO implements OrdersDAO {
    private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("sql_statements");
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    private final DataSource ds;

    public MySQLOrdersDAO(DataSource ds) {
        this.ds = ds;
    }

    @Override public List<Order> getAllOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(BUNDLE.getString("GET_ALL_ORDERS_BY_USER_ID"))) {

            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = createOrderFromParameters(rs);
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("get order by user id SQL error " + e);
            return null;
        }
        return orders;
    }

    @Override public boolean addOrder(Order order) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("INSERT_ORDER"));
            ps.setInt(1, order.getUser().getId());
            ps.setInt(2, order.getCar().getIdCar());
            ps.setDate(3, Date.valueOf(order.getStartDate()));
            ps.setDate(4, Date.valueOf(order.getEndDate()));
            ps.setString(5, order.getComment());
            ps.setBigDecimal(6, order.getRent());
            ps.setBigDecimal(7, order.getFine());
            ps.setString(8, order.getStatus().toString());
            LOGGER.debug(ps);
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("ADD order SQL error " + e);
            return false;
        }
        return true;
    }

    @Override
    public List<Order> getAllOrdersByStatus(OrderStatus status) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(BUNDLE.getString("GET_ALL_ORDERS_BY_STATUS"))) {

            statement.setString(1, status.toString().toLowerCase());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = createOrderFromParameters(rs);
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("get order by status id SQL error " + e);
            return null;
        }
        return orders;
    }

    private Order createOrderFromParameters(ResultSet rs) throws SQLException {
        User user = new User(
                rs.getInt("users.user_id"),
                rs.getString("users.first_name"),
                rs.getString("users.last_name"),
                rs.getString("users.passport"),
                rs.getString("users.email"),
                rs.getBoolean("users.is_admin"),
                rs.getString("users.password")
        );
        Brand brand = new Brand(
                rs.getInt("brands.brand_id"),
                rs.getString("brands.brand_name"),
                rs.getString("brands.brand_full_name")
        );
        Model model = new Model(
                rs.getInt("models.model_id"),
                brand,
                rs.getString("models.class_name"),
                rs.getString("models.model_name"),
                rs.getInt("models.doors_qty"),
                rs.getBoolean("models.automat"),
                rs.getDouble("models.power"),
                rs.getBoolean("models.conditioning"),
                rs.getString("models.img")
        );
        Car car = new Car(
                rs.getInt("cars.car_id"),
                model,
                rs.getInt("cars.year"),
                FuelType.valueOf(rs.getString("cars.fuel_type").toUpperCase()),
                rs.getBigDecimal("cars.price_day")
        );
        Order order = new Order(
                rs.getInt("orders.order_id"),
                user,
                car,
                rs.getDate("orders.date_start").toLocalDate(),
                rs.getDate("orders.date_end").toLocalDate(),
                rs.getString("orders.comment"),
                rs.getBigDecimal("orders.rent"),
                rs.getBigDecimal("orders.fine"),
                OrderStatus.valueOf(rs.getString("orders.status").toUpperCase())
        );
        return order;
    }

    @Override public boolean setOrderStatusById(Integer orderId, OrderStatus status) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(BUNDLE.getString("SET_ORDER_STATUS_BY_ID"))) {
            statement.setString(1, status.toString().toLowerCase());
            statement.setInt(2, orderId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Error by updating order by id");
            return false;
        }
    }

    @Override public boolean setOrderStatusCommentById(Integer orderId, OrderStatus status, String comment) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(BUNDLE.getString
                     ("SET_ORDER_STATUS_COMMENT_BY_ID"))) {
            statement.setString(1, status.toString().toLowerCase());
            statement.setString(2, comment);
            statement.setInt(3, orderId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Error by updating order by id");
            return false;
        }
    }

    @Override public boolean setOrderStatusCommentFineById(Integer orderId, OrderStatus status, String comment, BigDecimal fine) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(BUNDLE.getString("SET_ORDER_STATUS_COMMENT_FINE_BY_ID"))) {
            statement.setString(1, status.toString().toLowerCase());
            statement.setString(2, comment);
            statement.setBigDecimal(3, fine);
            statement.setString(4, orderId.toString());
            LOGGER.debug(" complete order SQL=" + statement);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Error by updating order by id");
            return false;
        }
    }

    @Override public Order getOrderById(Integer orderId) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(BUNDLE.getString("GET_ORDER_BY_ID"))) {

            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createOrderFromParameters(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error("Error by getting order by id");
            return null;
        }
    }

    @Override public OrderStatus getOrderStatusById(Integer orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            return order.getStatus();
        } else {
            return null;
        }
    }
}
