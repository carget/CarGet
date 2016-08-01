package ua.mishkyroff.carget.commands.get;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestAttributes;
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
    public JspPages execute(IRequestWrapper wrapper) {

        wrapper.setRequestAttribute(RequestAttributes.ORDERS, DAOFactory.getInstance().getOrdersDAO()
                .getAllOrdersByStatus(OrderStatus.COMPLETED));
        return JspPages.ADMIN_COMPLETED_ORDERS;
    }
}
