package ua.mishkyroff.carget.commands.get;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.OrderStatus;

/**
 * A command for process GET requests with "/admin_rejected_orders" uri
 * The command receives list of all rejected orders and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class RejectedOrdersCommand implements Command {
    @Override
    public String execute(IRequestWrapper wrapper) {
        wrapper.setSessionAttribute(SessionAttributes.ORDERS, DAOFactory.getInstance().getOrdersDAO()
                .getAllOrdersByStatus(OrderStatus.REJECTED));
        return REJECTED_ORDERS;
    }
}
