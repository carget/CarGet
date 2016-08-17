package ua.mishkyroff.carget.model.commands.idempotent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.commands.Command;

import java.util.List;


/**
 * A command for process GET requests with "/admin_paid_orders" uri
 * The command gets list of paid orders and forwards for corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class PaidOrdersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public View execute(IRequestWrapper wrapper) {
        DAOManager daoManager = wrapper.getDAOManager();
        List<Order> orders = (List<Order>) daoManager.openExecuteAndClose(
                manager -> manager.getOrdersDAO().getAllOrdersByStatus(Order.PAID));
        if (orders == null) {
            return View.INDEX;
        }
        wrapper.setRequestAttribute(RequestAttributes.ORDERS, orders);
        return View.ADMIN_PAID_ORDERS;
    }
}
