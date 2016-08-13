package ua.mishkyroff.carget.commands.get;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.OrderStatus;

/**
 * A command for process GET requests with "/admin_new_orders" uri
 * The command receives list of all new orders and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class NewOrdersCommand implements Command {
    @Override
    public JspPages execute(IRequestWrapper wrapper) {
        DAOFactory daoFactory = wrapper.getDAOFactory();
        wrapper.setRequestAttribute(RequestAttributes.ORDERS, daoFactory.getOrdersDAO()
                .getAllOrdersByStatus(OrderStatus.NEW));
        return JspPages.ADMIN_NEW_ORDERS;
    }
}
