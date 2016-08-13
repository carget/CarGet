package ua.mishkyroff.carget.commands.post;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.commands.Messages;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.OrderStatus;

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
    public JspPages execute(IRequestWrapper wrapper) {

        String orderId = wrapper.getParameter("order_id");
        if (orderId == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_PAY_ORDER);
            return JspPages.USER_MY_ORDERS;
        }
        DAOFactory daoFactory = wrapper.getDAOFactory();
        Integer orderIdInt = Integer.parseInt(orderId);
        OrderStatus orderStatus = daoFactory.getOrdersDAO().getOrderStatusById(orderIdInt);
        if (orderStatus == OrderStatus.APPROVED) {
            if (daoFactory.getOrdersDAO().setOrderStatusById(orderIdInt, OrderStatus.PAID)) {
                return JspPages.USER_MY_ORDERS;
            }
        }
        wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_PAY_ORDER);
        return JspPages.USER_MY_ORDERS;
    }
}
