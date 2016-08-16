package ua.mishkyroff.carget.model.commands.post;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process POST requests with "/admin_reject_order" uri
 * The command changes order's status to "rejected" for given order_id request parameter
 * Also the command sets comment and fine for completed order
 * The command sets error message if "order_id" request parameter doesn't exist
 * and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class RejectOrderCommand implements Command {
    @Override
    public View execute(IRequestWrapper wrapper) {

        String orderIdFromRequest = wrapper.getParameter("order_id");
        if (orderIdFromRequest == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_ORDER);
            return View.ADMIN_NEW_ORDERS;
        }
        Integer orderId = Integer.valueOf(orderIdFromRequest);
        String reason = wrapper.getParameter("reason");
        if (reason == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ENTER_REASON_FOR_REJECT);
            return View.ADMIN_NEW_ORDERS;
        }
        AbstractDAOFactory daoFactory = wrapper.getDAOFactory();
        Integer orderStatus = daoFactory.getOrdersDAO().getOrderStatusById(orderId);
        if (orderStatus == Order.NEW) {
            if (daoFactory.getOrdersDAO().setOrderStatusCommentById(orderId, Order.REJECTED, reason)) {
                return View.ADMIN_NEW_ORDERS;
            }
        }
        wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_REJECTING_ORDER);
        return View.ADMIN_NEW_ORDERS;
    }
}
