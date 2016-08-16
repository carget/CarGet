package ua.mishkyroff.carget.model.commands.post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process POST requests with "/admin_approve_order" uri
 * The command changes order's status to "approved" for given order_id request parameter
 * The command sets error message if "order_id" request parameter doesn't exist
 * and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class ApproveOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public View execute(IRequestWrapper wrapper) {
        String orderId = wrapper.getParameter("order_id");
        if (orderId == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_APPROVING_ORDER);
            return View.INDEX;
        }
        //todo final? TRANSACTION
        Integer orderIdInt = Integer.parseInt(orderId);
        DAOManager daoManager = wrapper.getDAOManager();
        Integer orderStatus = (Integer) daoManager.openExecuteAndClose(
                manager -> manager.getOrdersDAO().getOrderStatusById(orderIdInt));
        if (orderStatus != null && orderStatus == Order.NEW) {
            boolean result = (boolean) daoManager.openExecuteAndClose(
                    manager -> manager.getOrdersDAO().setOrderStatusById(orderIdInt, Order.APPROVED));
            if (result) {
                return View.ADMIN_NEW_ORDERS;
            }
        }
        //*******************************
        wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_APPROVING_ORDER);
        return View.INDEX;

    }
}
