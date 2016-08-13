package ua.mishkyroff.carget.commands.get;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.commands.Messages;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestAttributes;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.Order;

/**
 * A command for process GET requests with "/admin_return_car" uri
 * The command receives order for given order_id request parameter
 * The command sets error message if "order_id" request parameter doesn't exist
 * and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class ReturnCarCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public JspPages execute(IRequestWrapper wrapper) {
        String orderIdFromRequest = wrapper.getParameter("order_id");
        if (orderIdFromRequest == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_ORDER);
            return JspPages.ADMIN_APPROVED_ORDERS;
        }
        Integer orderId = Integer.valueOf(orderIdFromRequest);

        DAOFactory daoFactory = wrapper.getDAOFactory();
        Order order = daoFactory.getOrdersDAO().getOrderById(orderId);
        wrapper.setRequestAttribute(RequestAttributes.ORDER, order);
        return JspPages.ADMIN_RETURN_CAR;
    }
}
