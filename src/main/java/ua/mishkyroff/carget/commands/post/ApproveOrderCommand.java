package ua.mishkyroff.carget.commands.post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.commands.Messages;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.OrderStatus;

/**
 * A command for process POST requests with "/admin_approve_order" uri
 * The command changes order's status to "approved" for given order_id request parameter
 * The command sets error message if "order_id" request parameter doesn't exist
 * and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class ApproveOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public JspPages execute(IRequestWrapper wrapper) {
        String orderId = wrapper.getParameter("order_id");
        if (orderId == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_APPROVING_ORDER);
            return JspPages.INDEX;
        }
        DAOFactory daoFactory = wrapper.getDAOFactory();

        Integer orderIdInt = Integer.parseInt(orderId);

        OrderStatus orderStatus = daoFactory.getOrdersDAO().getOrderStatusById(orderIdInt);
        if (orderStatus == OrderStatus.NEW) {
            if (daoFactory.getOrdersDAO().setOrderStatusById(orderIdInt, OrderStatus.APPROVED)) {
                return JspPages.ADMIN_NEW_ORDERS;
            }
        }
        wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_APPROVING_ORDER);
        return JspPages.INDEX;

    }
}
