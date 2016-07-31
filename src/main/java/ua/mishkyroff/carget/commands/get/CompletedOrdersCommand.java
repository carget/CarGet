package ua.mishkyroff.carget.commands.get;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.OrderStatus;

/**
 * A command for process GET requests with "/admin_completed_orders" uri
 * The command receive a list with all completed orders
 * and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class CompletedOrdersCommand implements Command {
    @Override
    public String execute(IRequestWrapper wrapper) {

        wrapper.setSessionAttribute(SessionAttributes.ORDERS, DAOFactory.getInstance().getOrdersDAO()
                .getAllOrdersByStatus(OrderStatus.COMPLETED));
        return COMPLETED_ORDERS;
    }
}
