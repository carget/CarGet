package ua.mishkyroff.carget.commands.post;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestWrapper;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.dao.OrdersDAO;
import ua.mishkyroff.carget.entities.OrderStatus;

import static org.junit.Assert.assertEquals;
import static ua.mishkyroff.carget.commands.Messages.ERROR_COMPLETING_ORDER;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public class CompleteOrderCommandTest extends Mockito{

    private static Command command;
    private static IRequestWrapper wrapper;
    private static DAOFactory daoFactory;
    private static OrdersDAO ordersDAO;

    @Before
    public void setUp() throws Exception {
        command = new CompleteOrderCommand();
        wrapper = mock(RequestWrapper.class);
        daoFactory = mock(DAOFactory.class);
        ordersDAO = mock(OrdersDAO.class);
    }

    @After
    public void tearDown() throws Exception {
        command = null;
        daoFactory = null;
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
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
        when(ordersDAO.getOrderStatusById(Integer.parseInt(orderId))).thenReturn(OrderStatus.PAID);
        when(ordersDAO.setOrderStatusCommentFineById(Integer.valueOf(orderId), OrderStatus.COMPLETED,
                comment, Double.parseDouble(fine))).thenReturn(true);
        JspPages page = command.execute(wrapper);
        assertEquals(JspPages.ADMIN_COMPLETED_ORDERS, page);
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
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
        when(ordersDAO.getOrderStatusById(Integer.parseInt(orderId))).thenReturn(OrderStatus.REJECTED);
        when(ordersDAO.setOrderStatusCommentFineById(Integer.valueOf(orderId), OrderStatus.COMPLETED,
                comment, Double.valueOf(fine))).thenReturn(true);
        JspPages page = command.execute(wrapper);
        assertEquals(JspPages.INDEX, page);
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

        JspPages page = command.execute(wrapper);

        assertEquals(JspPages.INDEX ,page);
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
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
        when(ordersDAO.setOrderStatusCommentFineById(anyInt(), any(OrderStatus.class),
                anyString(), anyDouble())).thenReturn(false);

        JspPages page = command.execute(wrapper);

        assertEquals(JspPages.INDEX ,page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ERROR_COMPLETING_ORDER);

    }
}