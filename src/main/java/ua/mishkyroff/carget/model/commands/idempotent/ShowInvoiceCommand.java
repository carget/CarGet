package ua.mishkyroff.carget.model.commands.idempotent;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process GET requests with "/user_invoice" uri
 * Gets fine amount from by order if and shows invoice for fine payment
 *
 * @author Anton Mishkyroff
 */
public class ShowInvoiceCommand implements Command {
    @Override
    public View execute(IRequestWrapper wrapper) {

        String orderId = wrapper.getParameter("order_id");
        if (orderId == null) {
            return View.INDEX;
        }
        Integer id = Integer.parseInt(orderId);
        DAOManager daoManager = wrapper.getDAOManager();
        Order order = (Order) daoManager.openExecuteAndClose(
                manager -> manager.getOrdersDAO().getOrderById(id));
        if (order == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_GETTING_ORDER);
            return View.USER_MY_ORDERS;
        }
        if (order.getUser().getId() != (int) wrapper.getSessionAttribute(SessionAttributes.USER_ID)) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages
                    .ERROR_YOU_CAN_VIEW_ONLY_YOUR_INVOICES);
            return View.USER_MY_ORDERS;
        }
        wrapper.setRequestAttribute(RequestAttributes.ORDER, order);
        return View.USER_INVOICE;
    }
}
