package ua.mishkyroff.carget.model.commands.get;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.dao.Exceptions.DBException;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.commands.Command;

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
    public View execute(IRequestWrapper wrapper) {
        String orderIdFromRequest = wrapper.getParameter("order_id");
        if (orderIdFromRequest == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_ORDER);
            return View.ADMIN_APPROVED_ORDERS;
        }
        Integer orderId = Integer.valueOf(orderIdFromRequest);
        DAOManager daoManager = wrapper.getDAOManager();
        Order order = null;
        try {
            daoManager.openConnection();
            order = daoManager.getOrdersDAO().getOrderById(orderId);
        } catch (DBException e) {
            LOGGER.error(e);
        } finally {
            daoManager.closeConnection();
        }
        if (order == null) {
            return View.INDEX;
        }
        wrapper.setRequestAttribute(RequestAttributes.ORDER, order);
        return View.ADMIN_RETURN_CAR;
    }
}
