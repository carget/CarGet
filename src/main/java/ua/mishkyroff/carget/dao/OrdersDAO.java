package ua.mishkyroff.carget.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.entities.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class {@code OrdersDAO} used for CRUD and other useful DB operations with Order entity
 *
 * @author Anton Mishkyroff
 */
public class OrdersDAO {
    private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("sql_statements");
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    //todo make abstract DAO with default constructor with DataSource
    private final DataSource ds;

    OrdersDAO(DataSource ds) {
        this.ds = ds;
    }

    public List<Order> getAllOrdersByUserId(int userId) {
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

    public boolean addOrder(Order order) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(BUNDLE.getString("INSERT_ORDER"));
            ps.setString(1, String.valueOf(order.getUser().getId()));
            ps.setString(2, String.valueOf(order.getCar().getIdCar()));
            ps.setString(3, toMySQLDate(order.getStartDate()));
            ps.setString(4, toMySQLDate(order.getEndDate()));
            ps.setString(5, order.getComment());
            ps.setString(6, String.valueOf(order.getRent()));
            ps.setString(7, String.valueOf(order.getFine()));
            ps.setString(8, order.getStatus().toString());
            LOGGER.debug(ps);
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("ADD order SQL error " + e);
            return false;
        }
        return true;
    }

    private String toMySQLDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

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
        User user = new User();
        user.setId(rs.getInt("users.user_id"));
        user.setFirstName(rs.getString("users.first_name"));
        user.setLastName(rs.getString("users.last_name"));
        user.setEmail(rs.getString("users.email"));
        user.setPassport(rs.getString("users.passport"));
        user.setAdmin(rs.getBoolean("users.is_admin"));
        Car car = new Car();
        Model model = new Model();
        Brand brand = new Brand();
        brand.setIdBrand(rs.getInt("brands.brand_id"));
        brand.setBrandAbbr(rs.getString("brands.brand_name"));
        brand.setFullName(rs.getString("brands.brand_full_name"));
        model.setBrand(brand);
        model.setIdModel(rs.getInt("models.model_id"));
        model.setClassName(rs.getString("models.class_name"));
        model.setModelName(rs.getString("models.model_name"));
        model.setDoorsQty(rs.getInt("models.doors_qty"));
        model.setAutomat(rs.getBoolean("models.automat"));
        model.setPower(rs.getDouble("models.power"));
        model.setCondition(rs.getBoolean("models.conditioning"));
        car.setIdCar(rs.getInt("cars.car_id"));
        car.setModel(model);
        car.setYear(rs.getInt("cars.year"));
        car.setFuelType(FuelType.valueOf(rs.getString("cars.fuel_type").toUpperCase()));
        car.setPricePerDay(rs.getDouble("cars.price_day"));
        Order order = new Order();
        order.setIdOrder(rs.getInt("orders.order_id"));
        order.setUser(user);
        order.setCar(car);
        order.setStartDate(rs.getDate("orders.date_start"));
        order.setEndDate(rs.getDate("orders.date_end"));
        order.setComment(rs.getString("orders.comment"));
        order.setRent(rs.getDouble("orders.rent"));
        order.setFine(rs.getDouble("orders.fine"));
        order.setStatus(OrderStatus.valueOf(rs.getString("orders.status").toUpperCase()));
        return order;
    }

    public boolean setOrderStatusById(Integer orderId, OrderStatus status) {
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

    public boolean setOrderStatusCommentById(Integer orderId, OrderStatus status, String comment) {
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

    public boolean setOrderStatusCommentFineById(Integer orderId, OrderStatus status, String comment,
                                                 Double fine) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(BUNDLE.getString
                     ("SET_ORDER_STATUS_COMMENT_FINE_BY_ID"))) {
            statement.setString(1, status.toString().toLowerCase());
            statement.setString(2, comment);
            statement.setDouble(3, fine);
            statement.setString(4, orderId.toString());
            LOGGER.debug(" complete order SQL=" + statement);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Error by updating order by id");
            return false;
        }
    }

    public Order getOrderById(Integer orderId) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(BUNDLE.getString
                     ("GET_ORDER_BY_ID"))) {

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
}
