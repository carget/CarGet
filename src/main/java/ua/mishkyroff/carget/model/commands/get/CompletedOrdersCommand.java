package ua.mishkyroff.carget.model.commands.get;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process GET requests with "/admin_completed_orders" uri
 * The command receive a list with all completed orders
 * and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class CompletedOrdersCommand implements Command {
    @Override
    public View execute(IRequestWrapper wrapper) {

        AbstractDAOFactory daoFactory = wrapper.getDAOFactory();
        wrapper.setRequestAttribute(RequestAttributes.ORDERS, daoFactory.getOrdersDAO()
                .getAllOrdersByStatus(Order.COMPLETED));
        return View.ADMIN_COMPLETED_ORDERS;
    }
}
