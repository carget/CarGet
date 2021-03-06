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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test class for MyOrdersCommand.class
 *
 * @author Anton Mishkyroff
 */
public class MyOrdersCommandTest extends Mockito{
    private static IRequestWrapper wrapper;
    private static DAOManager daoManager;
    private static List<Order> orderList;
    private static Command command;

    @Before
    public void setUp() throws Exception {
        wrapper = mock(RequestWrapper.class);
        daoManager = mock(DAOManager.class);
        command = new  MyOrdersCommand();
        orderList = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
        wrapper = null;
        daoManager = null;
        command = null;
    }

    @Test
    public void execute() throws Exception {
        final Integer userId = 1;
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(wrapper.getSessionAttribute(SessionAttributes.USER_ID)).thenReturn(userId);
        when(daoManager.openExecuteAndClose(any(DAOCommand.class))).thenReturn(orderList);

        View page = command.execute(wrapper);

        assertEquals(View.USER_MY_ORDERS, page);
        verify(wrapper, times(1)).setRequestAttribute(RequestAttributes.ORDERS, orderList);
    }
}