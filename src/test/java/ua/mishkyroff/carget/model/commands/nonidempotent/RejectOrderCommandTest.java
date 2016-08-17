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
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.commands.Command;

import static org.junit.Assert.assertEquals;

/**
 * Test class for RejectOrderCommand.class
 *
 * @author Anton Mishkyroff
 */
public class RejectOrderCommandTest extends Mockito {
    private static Command command;
    private static IRequestWrapper wrapper;
    private static DAOManager daoManager;

    @Before
    public void setUp() throws Exception {
        command = new RejectOrderCommand();
        wrapper = mock(RequestWrapper.class);
        daoManager = mock(DAOManager.class);
    }

    @After
    public void tearDown() throws Exception {
        command = null;
        wrapper = null;
        daoManager = null;
    }

    @Test
    public void correctParams() throws Exception {
        String orderId = "1";
        String reason = "empty";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.transaction(any(DAOCommand.class))).thenReturn(true);
        View page = command.execute(wrapper);
        assertEquals(View.ADMIN_NEW_ORDERS, page);
        verify(wrapper, times(0)).setSessionAttribute(any(SessionAttributes.class), anyString());
    }

    @Test
    public void incorrectOrderStatus() throws Exception {
        String orderId = "1";
        String reason = "empty";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.transaction(any(DAOCommand.class))).thenReturn(false);
        View page = command.execute(wrapper);
        assertEquals(View.ADMIN_NEW_ORDERS, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE,
                Messages.ERROR_REJECTING_ORDER);
    }

    @Test
    public void incorrectOrderId() throws Exception {
        String orderId = null;
        String reason = "empty";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        View page = command.execute(wrapper);
        assertEquals(View.ADMIN_NEW_ORDERS, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE,
                Messages.FIRSTLY_CHOOSE_ORDER);

    }

    @Test
    public void incorrectReason() throws Exception {
        String orderId = "1";
        String reason = null;
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getParameter("reason")).thenReturn(reason);
        View page = command.execute(wrapper);
        assertEquals(View.ADMIN_NEW_ORDERS, page);
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
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.transaction(any(DAOCommand.class))).thenReturn(false);

        View page = command.execute(wrapper);

        assertEquals(View.ADMIN_NEW_ORDERS, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_REJECTING_ORDER);

    }
}