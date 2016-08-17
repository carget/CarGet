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
import ua.mishkyroff.carget.model.commands.Command;

import static org.junit.Assert.assertEquals;
import static ua.mishkyroff.carget.model.Messages.ERROR_APPROVING_ORDER;

/**
 * Test class for ApproveOrderCommand.class
 *
 * @author Anton Mishkyroff
 */
public class ApproveOrderCommandTest extends Mockito {

    private static Command command;
    private static IRequestWrapper wrapper;
    private static DAOManager daoManager;

    @Before
    public void setUp() throws Exception {
        command = new ApproveOrderCommand();
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
    public void correctOrderIdAndStatus() throws Exception {
        String orderId = "1";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.transaction(any(DAOCommand.class))).thenReturn(true);
        View page = command.execute(wrapper);
        assertEquals(View.ADMIN_NEW_ORDERS, page);
        verify(wrapper, times(0)).setSessionAttribute(any(SessionAttributes.class), anyString());

    }

    @Test
    public void correctOrderIdAndIncorrectStatus() throws Exception {
        String orderId = "1";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.transaction(any(DAOCommand.class))).thenReturn(false);
        View page = command.execute(wrapper);
        assertEquals(View.INDEX, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE,
                ERROR_APPROVING_ORDER);

    }

    @Test
    public void incorrectOrderId() throws Exception {
        String orderId = null;
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        View page = command.execute(wrapper);
        assertEquals(View.INDEX, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE,
                ERROR_APPROVING_ORDER);

    }

}