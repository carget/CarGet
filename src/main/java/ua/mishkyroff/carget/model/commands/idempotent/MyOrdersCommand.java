package ua.mishkyroff.carget.model.commands.idempotent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.commands.Command;

import java.util.List;

/**
 * A command for process GET requests with "/user_my_orders" uri
 * The command receives list of orders for
 * user_id parameter from session and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class MyOrdersCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public View execute(IRequestWrapper wrapper) {
        DAOManager daoManager = wrapper.getDAOManager();
        List<Order> orders = (List<Order>) daoManager.openExecuteAndClose(
                manager -> manager.getOrdersDAO().getAllOrdersByUserId
                        ((Integer) wrapper.getSessionAttribute(SessionAttributes.USER_ID)));
        if (orders == null) {
            return View.INDEX;
        }
        wrapper.setRequestAttribute(RequestAttributes.ORDERS, orders);
        return View.USER_MY_ORDERS;
    }

}
