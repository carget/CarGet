package ua.mishkyroff.carget.model.commands.idempotent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.*;
import ua.mishkyroff.carget.dao.DAOCommand;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.model.commands.Command;

import static org.junit.Assert.assertEquals;
import static ua.mishkyroff.carget.model.Messages.FIRSTLY_CHOOSE_ORDER;

/**
 * Test class for ReturnCarCommand.class
 *
 * @author Anton Mishkyroff
 */
public class ReturnCarCommandTest extends Mockito{

    private static Command command;
    private static IRequestWrapper wrapper;
    private static DAOManager daoManager;
    private static Order order;


    @Before
    public void setUp() throws Exception {
        command = new ReturnCarCommand();
        wrapper = mock(RequestWrapper.class);
        daoManager = mock(DAOManager.class);
        order = mock(Order.class);
    }

    @After
    public void tearDown() throws Exception {
        command = null;
        wrapper = null;
    }

    @Test
    public void parametersCorrect() throws Exception {
        String orderId = "1";
        when(wrapper.getParameter("order_id")).thenReturn(orderId);
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.openExecuteAndClose(any(DAOCommand.class))).thenReturn(order);

        View page = command.execute(wrapper);
        assertEquals(View.ADMIN_RETURN_CAR, page);
        verify(wrapper, times(1)).setRequestAttribute(RequestAttributes.ORDER, order);
    }

    @Test
    public void parametersIncorrect() throws Exception {
        String orderId = null;
        when(wrapper.getParameter("order_id")).thenReturn(orderId);

        View page = command.execute(wrapper);
        assertEquals(View.ADMIN_APPROVED_ORDERS, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, FIRSTLY_CHOOSE_ORDER);
    }
}