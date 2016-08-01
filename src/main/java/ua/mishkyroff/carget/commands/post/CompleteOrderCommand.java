package ua.mishkyroff.carget.commands.post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.OrderStatus;

/**
 * A command for process POST requests with "/admin_complete_order" uri
 * The command changes order's status to "complete" for given order_id request parameter
 * Also the command sets comment and fine to completed order
 * The command sets error message if "order_id" request parameter doesn't exist
 * and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class CompleteOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");


    @Override
    public JspPages execute(IRequestWrapper wrapper) {
        String orderId = wrapper.getParameter("order_id");
        String fine = wrapper.getParameter("fine");

        if (orderId == null || fine == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, ERROR_COMPLETING_ORDER);
            return JspPages.INDEX;
        }
        String comment = wrapper.getParameter("comment");
        comment = comment == null ? "" : comment;
        boolean orderCompleted = DAOFactory.getInstance().getOrdersDAO()
                .setOrderStatusCommentFineById(Integer.valueOf(orderId), OrderStatus.COMPLETED, comment, Double.valueOf(fine));
        LOGGER.debug(" order completed = " + orderCompleted);
        //TODO refresh orders object
        return JspPages.ADMIN_COMPLETED_ORDERS;
    }
}
