package ua.mishkyroff.carget.commands.get;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;

/**
 * A command for process GET requests with "/user_my_orders" uri
 * The command receives list of orders for
 * user_id parameter from session and forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 */
public class MyOrdersCommand implements Command{
    @Override
    public String execute(IRequestWrapper wrapper)  {
        wrapper.setSessionAttribute(SessionAttributes.ORDERS, DAOFactory.getInstance().getOrdersDAO()
                .getAllOrdersByUserId((Integer) wrapper.getSessionAttribute(SessionAttributes.USER_ID)));
        return MY_ORDERS;
    }
}
