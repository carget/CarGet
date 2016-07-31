package ua.mishkyroff.carget.commands.post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
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
    public String execute(IRequestWrapper wrapper) {
        Integer orderId = null;
        try {
            orderId = Integer.valueOf(wrapper.getParameter("order_id"));
        } catch (NumberFormatException e) {
            LOGGER.error(" can't parse order_id " + e.getMessage());
            return INDEX;
        }
        DAOFactory.getInstance().getOrdersDAO().setOrderStatusById(orderId, OrderStatus.APPROVED);
        return NEW_ORDERS;
    }
}
