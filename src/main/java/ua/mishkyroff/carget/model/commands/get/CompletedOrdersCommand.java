package ua.mishkyroff.carget.model.commands.get;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.dao.Exceptions.DBException;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.commands.Command;

import java.util.List;

/**
 * A command for process GET requests with "/admin_completed_orders" uri
 * The command receive a list with all completed orders
 * and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class CompletedOrdersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public View execute(IRequestWrapper wrapper) {
        DAOManager daoManager = wrapper.getDAOManager();
        List<Order> orders = null;
        try {
            daoManager.openConnection();
            orders = daoManager.getOrdersDAO().getAllOrdersByStatus(Order.COMPLETED);
            wrapper.setRequestAttribute(RequestAttributes.ORDERS, orders);
        } catch (DBException e) {
            LOGGER.error(e);
        } finally {
            daoManager.closeConnection();
        }
        if (orders == null) {
            return View.INDEX;
        }
        return View.ADMIN_COMPLETED_ORDERS;
    }
}
