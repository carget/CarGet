package ua.mishkyroff.carget.model.commands.get;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.*;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.dao.OrdersDAO;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.commands.Command;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public class MyOrdersCommandTest extends Mockito{
    private static IRequestWrapper wrapper;
    private static AbstractDAOFactory daoFactory;
    private static OrdersDAO ordersDAO;
    private static List<Order> orderList;
    private static Command command;

    @Before
    public void setUp() throws Exception {
        wrapper = mock(RequestWrapper.class);
        daoFactory = mock(AbstractDAOFactory.class);
        ordersDAO = mock(OrdersDAO.class);
        command = new  MyOrdersCommand();
        orderList = new ArrayList<>();
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
        final Integer userId = 1;
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(wrapper.getSessionAttribute(SessionAttributes.USER_ID)).thenReturn(userId);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
        when(ordersDAO.getAllOrdersByUserId(userId)).thenReturn(orderList);

        View page = command.execute(wrapper);

        assertEquals(page, View.USER_MY_ORDERS);
        verify(wrapper, times(1)).setRequestAttribute(RequestAttributes.ORDERS, orderList);
    }
}