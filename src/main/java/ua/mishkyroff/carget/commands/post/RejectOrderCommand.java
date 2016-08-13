package ua.mishkyroff.carget.commands.post;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.commands.Messages;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.OrderStatus;

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
    public JspPages execute(IRequestWrapper wrapper) {

        String orderIdFromRequest = wrapper.getParameter("order_id");
        if (orderIdFromRequest == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_ORDER);
            return JspPages.ADMIN_NEW_ORDERS;
        }
        Integer orderId = Integer.valueOf(orderIdFromRequest);
        String reason = wrapper.getParameter("reason");
        if (reason == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ENTER_REASON_FOR_REJECT);
            return JspPages.ADMIN_NEW_ORDERS;
        }
        DAOFactory daoFactory = wrapper.getDAOFactory();
        OrderStatus orderStatus = daoFactory.getOrdersDAO().getOrderStatusById(orderId);
        if (orderStatus == OrderStatus.NEW) {
            if (daoFactory.getOrdersDAO().setOrderStatusCommentById(orderId, OrderStatus
                    .REJECTED, reason)) {
                return JspPages.ADMIN_NEW_ORDERS;
            }
        }
        wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_REJECTING_ORDER);
        return JspPages.ADMIN_NEW_ORDERS;
    }
}
