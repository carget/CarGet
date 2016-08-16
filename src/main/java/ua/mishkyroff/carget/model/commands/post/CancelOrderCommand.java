package ua.mishkyroff.carget.model.commands.post;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process POST requests with "/admin_cancel_order" uri
 * The command changes order's status to "canceled" for given order_id request parameter
 * The command sets error message if "order_id" request parameter doesn't exist
 * and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class CancelOrderCommand implements Command {
    @Override
    public View execute(IRequestWrapper wrapper) {

        String orderIdFromRequest = wrapper.getParameter("order_id");
        if (orderIdFromRequest == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_ORDER);
            return View.ADMIN_APPROVED_ORDERS;
        }
        Integer orderId = Integer.valueOf(orderIdFromRequest);
        String reason = wrapper.getParameter("reason");
        if (reason == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_ENTER_REASON_FOR_CANCEL);
            return View.ADMIN_APPROVED_ORDERS;
        }
        //todo transaction!!!!
        DAOManager daoManager = wrapper.getDAOManager();
        Integer orderStatus = (Integer) daoManager.openExecuteAndClose(
                manager -> manager.getOrdersDAO().getOrderStatusById(orderId));

        if (orderStatus!=null && orderStatus == Order.APPROVED) {
            boolean result = (boolean) daoManager.openExecuteAndClose(
                    manager -> manager.getOrdersDAO().setOrderStatusCommentById(orderId, Order.CANCELED, reason));
            if (result) {
                return View.ADMIN_APPROVED_ORDERS;
            }
        }
        wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_CANCELING_ORDER);
        return View.ADMIN_APPROVED_ORDERS;
    }
}
