package ua.mishkyroff.carget.commands.get;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestAttributes;
import ua.mishkyroff.carget.controllers.RequestWrapper;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.dao.OrdersDAO;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.entities.OrderStatus;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public class RejectedOrdersCommandTest {
    private static IRequestWrapper wrapper;
    private static DAOFactory daoFactory;
    private static OrdersDAO ordersDAO;
    private static List<Order> orderList;
    private static Command command;

    @Before
    public void setUp() throws Exception {
        wrapper = mock(RequestWrapper.class);
        daoFactory = mock(DAOFactory.class);
        ordersDAO = mock(OrdersDAO.class);
        command = new RejectedOrdersCommand();
    }

    @After
    public void tearDown() throws Exception {
        wrapper = null;
        daoFactory = null;
        ordersDAO = null;
        command = null;
    }

    @Test
    public void execute() throws Exception {
        OrderStatus status = OrderStatus.REJECTED;
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
        when(ordersDAO.getAllOrdersByStatus(status)).thenReturn(orderList);

        JspPages page = command.execute(wrapper);

        assertEquals(page, JspPages.ADMIN_REJECTED_ORDERS);
        verify(wrapper, times(1)).setRequestAttribute(RequestAttributes.ORDERS, orderList);
    }

}