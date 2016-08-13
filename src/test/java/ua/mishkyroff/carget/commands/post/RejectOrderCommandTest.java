package ua.mishkyroff.carget.commands.post;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.commands.Messages;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestWrapper;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.dao.OrdersDAO;
import ua.mishkyroff.carget.entities.OrderStatus;

import static org.junit.Assert.assertEquals;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public class RejectOrderCommandTest extends Mockito {
    private static Command command;
    private static IRequestWrapper wrapper;
    private static DAOFactory daoFactory;
    private static OrdersDAO ordersDAO;

    @Before
    public void setUp() throws Exception {
        command = new RejectOrderCommand();
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
        String reason = "empty";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
        when(ordersDAO.getOrderStatusById(Integer.parseInt(orderId))).thenReturn(OrderStatus.NEW);
        when(ordersDAO.setOrderStatusCommentById(Integer.parseInt(orderId), OrderStatus
                .REJECTED, reason)).thenReturn(true);
        JspPages page = command.execute(wrapper);
        assertEquals(JspPages.ADMIN_NEW_ORDERS, page);
        verify(wrapper, times(0)).setSessionAttribute(any(SessionAttributes.class), anyString());
    }

    @Test
    public void incorrectOrderStatus() throws Exception {
        String orderId = "1";
        String reason = "empty";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
        when(ordersDAO.getOrderStatusById(Integer.parseInt(orderId))).thenReturn(OrderStatus.COMPLETED);
        when(ordersDAO.setOrderStatusCommentById(Integer.parseInt(orderId), OrderStatus
                .REJECTED, reason)).thenReturn(true);
        JspPages page = command.execute(wrapper);
        assertEquals(JspPages.ADMIN_NEW_ORDERS, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE,
                Messages.ERROR_REJECTING_ORDER);
    }

    @Test
    public void incorrectOrderId() throws Exception {
        String orderId = null;
        String reason = "empty";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        JspPages page = command.execute(wrapper);
        assertEquals(JspPages.ADMIN_NEW_ORDERS, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE,
                Messages.FIRSTLY_CHOOSE_ORDER);

    }

    @Test
    public void incorrectReason() throws Exception {
        String orderId = "1";
        String reason = null;
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        JspPages page = command.execute(wrapper);
        assertEquals(JspPages.ADMIN_NEW_ORDERS, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE,
                Messages.ENTER_REASON_FOR_REJECT);

    }

    @Test
    public void DAOFails() throws Exception {
        String orderId = "1";
        String fine = "10";
        String reason = "";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("fine")).thenReturn(fine);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
        when(ordersDAO.setOrderStatusCommentById(anyInt(), any(OrderStatus.class), anyString()))
                .thenReturn(false);

        JspPages page = command.execute(wrapper);

        assertEquals(JspPages.ADMIN_NEW_ORDERS, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_REJECTING_ORDER);

    }
}