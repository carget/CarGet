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
public class CancelOrderCommandTest extends Mockito{

    private static Command command;
    private static IRequestWrapper wrapper;
    private static DAOFactory daoFactory;
    private static OrdersDAO ordersDAO;

    @Before
    public void setUp() throws Exception {
        command = new CancelOrderCommand();
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
    public void correctOrderIdReasonAndStatus() throws Exception {
        String orderId = "1";
        String reason = "reason";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
        when(ordersDAO.getOrderStatusById(Integer.parseInt(orderId))).thenReturn(OrderStatus.APPROVED);
        when(ordersDAO.setOrderStatusCommentById(Integer.parseInt(orderId), OrderStatus.CANCELED, reason))
                .thenReturn(true);
        JspPages page = command.execute(wrapper);
        assertEquals(JspPages.ADMIN_APPROVED_ORDERS,page);
        verify(wrapper, times(0)).setSessionAttribute(any(SessionAttributes.class), anyString());

    }

    @Test
    public void correctOrderIdReasonAndIncorrectStatus() throws Exception {
        String orderId = "1";
        String reason = "reason";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
        when(ordersDAO.getOrderStatusById(Integer.parseInt(orderId))).thenReturn(OrderStatus.NEW);
        when(ordersDAO.setOrderStatusCommentById(Integer.parseInt(orderId), OrderStatus.CANCELED, reason))
                .thenReturn(true);
        JspPages page = command.execute(wrapper);
        assertEquals(JspPages.ADMIN_APPROVED_ORDERS,page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE,
                Messages.ERROR_CANCELING_ORDER);
    }

    @Test
    public void incorrectOrderId() throws Exception {
        String orderId = null;
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        JspPages page = command.execute(wrapper);
        assertEquals(JspPages.ADMIN_APPROVED_ORDERS , page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_ORDER);

    }

    @Test
    public void correctOrderIdIncorrectReason() throws Exception {
        String orderId = "1";
        String reason = null;
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        JspPages page = command.execute(wrapper);
        assertEquals(JspPages.ADMIN_APPROVED_ORDERS , page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE,
                Messages.ERROR_ENTER_REASON_FOR_CANCEL);

    }

}