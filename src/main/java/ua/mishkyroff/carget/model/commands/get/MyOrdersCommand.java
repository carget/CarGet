package ua.mishkyroff.carget.model.commands.get;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process GET requests with "/user_my_orders" uri
 * The command receives list of orders for
 * user_id parameter from session and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class MyOrdersCommand implements Command {
    @Override
    public View execute(IRequestWrapper wrapper) {
        AbstractDAOFactory daoFactory = wrapper.getDAOFactory();
        wrapper.setRequestAttribute(RequestAttributes.ORDERS, daoFactory
                .getOrdersDAO().getAllOrdersByUserId
                        ((Integer) wrapper.getSessionAttribute(SessionAttributes.USER_ID)));
        return View.USER_MY_ORDERS;
    }
}
