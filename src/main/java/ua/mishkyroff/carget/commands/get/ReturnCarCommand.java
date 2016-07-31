package ua.mishkyroff.carget.commands.get;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.Order;

/**
 * A command for process GET requests with "/admin_return_car" uri
 * The command receives of order for given order_id request parameter
 * The command sets error message if "order_id" request parameter doesn't exist
 * and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class ReturnCarCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public String execute(IRequestWrapper wrapper) {
        String orderIdFromRequest = wrapper.getParameter("order_id");
        if (orderIdFromRequest == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, FIRSTLY_CHOOSE_ORDER);
            return APPROVED_ORDERS;
        }
        Integer orderId = Integer.valueOf(orderIdFromRequest);
        Order order = DAOFactory.getInstance().getOrdersDAO().getOrderById(orderId);
        wrapper.setSessionAttribute(SessionAttributes.ORDER, order);
        return RETURN_CAR;
    }
}
