package ua.mishkyroff.carget.model.commands.nonidempotent;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process POST requests with "/user_payment_order" uri
 * The command changes order's status to "paid" for given order_id request parameter
 * The command sets error message if "order_id" request parameter doesn't exist
 * and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class PaymentOrderCommand implements Command {
    @Override
    public View execute(IRequestWrapper wrapper) {
        String orderId = wrapper.getParameter("order_id");
        if (orderId == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_PAY_ORDER);
            return View.USER_MY_ORDERS;
        }
        DAOManager daoManager = wrapper.getDAOManager();
        Integer orderIdInt = Integer.parseInt(orderId);
        boolean ok = (boolean) daoManager.transaction(
                manager -> {
                    Integer orderStatus = manager.getOrdersDAO().getOrderStatusById(orderIdInt);
                    if (orderStatus != null && orderStatus == Order.APPROVED) {
                        return manager.getOrdersDAO().setOrderStatusById(orderIdInt, Order.PAID);
                    }
                    return false;
                });
        if (ok) {
            return View.USER_MY_ORDERS;
        }
        wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_PAY_ORDER);
        return View.USER_MY_ORDERS;
    }
}
