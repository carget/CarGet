package ua.mishkyroff.carget.dao;

import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.entities.OrderStatus;

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

    List<Order> getAllOrdersByStatus(OrderStatus status);

    boolean setOrderStatusById(Integer orderId, OrderStatus status);

    boolean setOrderStatusCommentById(Integer orderId, OrderStatus status, String comment);

    boolean setOrderStatusCommentFineById(Integer orderId, OrderStatus status, String comment, BigDecimal fine);

    Order getOrderById(Integer orderId);

    OrderStatus getOrderStatusById(Integer orderId);
}
