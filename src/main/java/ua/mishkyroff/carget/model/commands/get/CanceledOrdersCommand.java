package ua.mishkyroff.carget.model.commands.get;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process GET requests with "/admin_canceled_orders" uri
 * The command gets list of canceled orders and forwards for corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class CanceledOrdersCommand implements Command{
    @Override
    public View execute(IRequestWrapper wrapper) {
        AbstractDAOFactory daoFactory = wrapper.getDAOFactory();
        wrapper.setRequestAttribute(RequestAttributes.ORDERS,
                daoFactory.getOrdersDAO().getAllOrdersByStatus(Order.CANCELED));
        return View.ADMIN_CANCELED_ORDERS;
    }
}
