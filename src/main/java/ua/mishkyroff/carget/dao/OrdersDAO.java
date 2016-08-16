package ua.mishkyroff.carget.dao;

import ua.mishkyroff.carget.entities.Order;

import java.math.BigDecimal;
import java.util.List;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public interface OrdersDAO {
    List<Order> getAllOrdersByUserId(int userId);

    boolean addOrder(Order order);

    List<Order> getAllOrdersByStatus(int status);

    boolean setOrderStatusById(Integer orderId, int status);

    boolean setOrderStatusCommentById(Integer orderId, int status, String comment);

    boolean setOrderStatusCommentFineById(Integer orderId, int status, String comment, BigDecimal
            fine);

    Order getOrderById(Integer orderId);

    Integer getOrderStatusById(Integer orderId);
}
