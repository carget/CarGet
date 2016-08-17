package ua.mishkyroff.carget.model.commands.idempotent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOCommand;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.entities.User;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.commands.Command;

import static org.junit.Assert.assertEquals;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public class ShowInvoiceCommandTest extends Mockito {

    private static IRequestWrapper wrapper;
    private static DAOManager daoManager;
    private static Command command;
    private static Order order;
    private static User user;


    @Before
    public void setUp() throws Exception {
        wrapper = mock(IRequestWrapper.class);
        daoManager = mock(DAOManager.class);
        command = new ShowInvoiceCommand();
        order = mock(Order.class);
        user = mock(User.class);
        when(wrapper.getDAOManager()).thenReturn(daoManager);

        when(wrapper.getParameter("order_id")).thenReturn("1");
    }

    @After
    public void tearDown() throws Exception {
        wrapper = null;
        daoManager = null;
        command = null;

    }

    @Test
    public void allCorrectParams() throws Exception {
        when(daoManager.openExecuteAndClose(any(DAOCommand.class))).thenReturn(order);
        when(order.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(wrapper.getSessionAttribute(SessionAttributes.USER_ID)).thenReturn(1);
        View page = command.execute(wrapper);

        assertEquals(View.USER_INVOICE, page);
        verify(wrapper, times(0)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_GETTING_ORDER);
        verify(wrapper, times(1)).setRequestAttribute(any(RequestAttributes.class), any(Order.class));

    }

    @Test
    public void orderIdNull() throws Exception {


        when(wrapper.getParameter("order_id")).thenReturn(null);
        View page = command.execute(wrapper);

        assertEquals(View.INDEX, page);
        verify(wrapper, times(0)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_GETTING_ORDER);
        verify(wrapper, times(0)).setRequestAttribute(any(RequestAttributes.class), any(Order.class));
    }

    @Test
    public void orderNull() throws Exception {

        when(daoManager.openExecuteAndClose(any(DAOCommand.class))).thenReturn(null);
        when(wrapper.getSessionAttribute(SessionAttributes.USER_ID)).thenReturn(1);
        View page = command.execute(wrapper);

        assertEquals(View.USER_MY_ORDERS, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_GETTING_ORDER);
        verify(wrapper, times(0)).setRequestAttribute(any(RequestAttributes.class), any(Order.class));

    }

    @Test
    public void incorrectUserId() throws Exception {

        when(daoManager.openExecuteAndClose(any(DAOCommand.class))).thenReturn(order);
        when(order.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(wrapper.getSessionAttribute(SessionAttributes.USER_ID)).thenReturn(2);

        View page = command.execute(wrapper);

        assertEquals(View.USER_MY_ORDERS, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages
                .ERROR_YOU_CAN_VIEW_ONLY_YOUR_INVOICES);
        verify(wrapper, times(0)).setRequestAttribute(any(RequestAttributes.class), any(Order.class));
        verify(wrapper, times(0)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_GETTING_ORDER);
    }
}