package ua.mishkyroff.carget.commands.get;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.OrderStatus;

/**
 * A command for process GET requests with "/admin_approved_orders" uri
 * The command gets list of approved orders and forwards for corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class ApprovedOrdersCommand implements Command {
    @Override
    public JspPages execute(IRequestWrapper wrapper) {
        DAOFactory daoFactory = wrapper.getDAOFactory();
        wrapper.setRequestAttribute(RequestAttributes.ORDERS,
                daoFactory.getOrdersDAO().getAllOrdersByStatus(OrderStatus.APPROVED));
        return JspPages.ADMIN_APPROVED_ORDERS;
    }
}
