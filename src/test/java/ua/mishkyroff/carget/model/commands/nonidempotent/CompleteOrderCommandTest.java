package ua.mishkyroff.carget.model.commands.nonidempotent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestWrapper;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOCommand;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.dao.OrdersDAO;
import ua.mishkyroff.carget.model.commands.Command;

import static org.junit.Assert.assertEquals;
import static ua.mishkyroff.carget.model.Messages.ERROR_COMPLETING_ORDER;

/**
 * Test class for CompleteOrderCommand.class
 *
 * @author Anton Mishkyroff
 */
public class CompleteOrderCommandTest extends Mockito{

    private static Command command;
    private static IRequestWrapper wrapper;
    private static DAOManager daoManager;
    private static OrdersDAO ordersDAO;

    @Before
    public void setUp() throws Exception {
        command = new CompleteOrderCommand();
        wrapper = mock(RequestWrapper.class);
        daoManager = mock(DAOManager.class);
        ordersDAO = mock(OrdersDAO.class);
    }

    @After
    public void tearDown() throws Exception {
        command = null;
        daoManager = null;
        ordersDAO = null;
    }

    @Test
    public void correctParams() throws Exception {
        String orderId = "1";
        String fine = "10.0";
        String comment = "empty";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("fine")).thenReturn(fine);
        when(wrapper.getParameter("comment")).thenReturn(comment);
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.getOrdersDAO()).thenReturn(ordersDAO);
        when(daoManager.transaction(any(DAOCommand.class))).thenReturn(true);
        View page = command.execute(wrapper);
        assertEquals(View.ADMIN_COMPLETED_ORDERS, page);
        verify(wrapper, times(0)).setSessionAttribute(any(SessionAttributes.class), anyString());
    }

    @Test
    public void incorrectOrderStatus() throws Exception {
        String orderId = "1";
        String fine = "10";
        String comment = "empty";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("fine")).thenReturn(fine);
        when(wrapper.getParameter("comment")).thenReturn(comment);
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.getOrdersDAO()).thenReturn(ordersDAO);
        when(daoManager.transaction(any(DAOCommand.class))).thenReturn(false);
        View page = command.execute(wrapper);
        assertEquals(View.INDEX, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ERROR_COMPLETING_ORDER);
    }
    @Test
    public void incorrectPrams() throws Exception {
        String orderId = "1";
        String fine = null;
        String comment = "";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("fine")).thenReturn(fine);
        when(wrapper.getParameter("comment")).thenReturn(comment);

        View page = command.execute(wrapper);

        assertEquals(View.INDEX ,page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ERROR_COMPLETING_ORDER);

    }

    @Test
    public void DAOFails() throws Exception {
        String orderId = "1";
        String fine = "10";
        String comment = "";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("fine")).thenReturn(fine);
        when(wrapper.getParameter("comment")).thenReturn(comment);
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.transaction(any(DAOCommand.class))).thenReturn(false);

        View page = command.execute(wrapper);

        assertEquals(View.INDEX ,page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ERROR_COMPLETING_ORDER);

    }
}