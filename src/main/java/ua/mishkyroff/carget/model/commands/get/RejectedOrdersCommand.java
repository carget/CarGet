package ua.mishkyroff.carget.model.commands.get;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.entities.OrderStatus;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process GET requests with "/admin_rejected_orders" uri
 * The command receives list of all rejected orders and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class RejectedOrdersCommand implements Command {
    @Override
    public View execute(IRequestWrapper wrapper) {
        AbstractDAOFactory daoFactory = wrapper.getDAOFactory();
        wrapper.setRequestAttribute(RequestAttributes.ORDERS, daoFactory
                .getOrdersDAO().getAllOrdersByStatus(OrderStatus.REJECTED));
        return View.ADMIN_REJECTED_ORDERS;
    }
}
