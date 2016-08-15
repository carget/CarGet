package ua.mishkyroff.carget.model.commands.post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.entities.OrderStatus;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.commands.Command;

import java.math.BigDecimal;

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
    public View execute(IRequestWrapper wrapper) {
        String orderId = wrapper.getParameter("order_id");
        String fine = wrapper.getParameter("fine");

        if (orderId == null || fine == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_COMPLETING_ORDER);
            return View.INDEX;
        }
        String comment = wrapper.getParameter("comment");
        comment = (comment == null) ? "" : comment;
        Integer orderIdInt = Integer.parseInt(orderId);
        AbstractDAOFactory daoFactory = wrapper.getDAOFactory();
        OrderStatus orderStatus = daoFactory.getOrdersDAO().getOrderStatusById(orderIdInt);
        if (orderStatus == OrderStatus.PAID) {
            if (daoFactory.getOrdersDAO()
                    .setOrderStatusCommentFineById(Integer.valueOf(orderId), OrderStatus.COMPLETED,
                            comment, new BigDecimal(fine))) {
                LOGGER.debug("Order completed!");
                return View.ADMIN_COMPLETED_ORDERS;
            }

        }
        wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_COMPLETING_ORDER);
        LOGGER.debug("Order not completed!");
        return View.INDEX;

    }
}
