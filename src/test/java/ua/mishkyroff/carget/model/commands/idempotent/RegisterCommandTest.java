package ua.mishkyroff.carget.model.commands.idempotent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.*;
import ua.mishkyroff.carget.model.commands.Command;

import static org.junit.Assert.assertEquals;

/**
 * Test class for RegisterCommand.class
 *
 * @author Anton Mishkyroff
 */
public class RegisterCommandTest extends Mockito {

    private static Command command;
    private static IRequestWrapper wrapper;

    @Before
    public void setUp() throws Exception {
        command = new RegisterCommand();
        wrapper = mock(RequestWrapper.class);
    }

    @After
    public void tearDown() throws Exception {
        command = null;
        wrapper = null;
    }

    @Test
    public void execute() throws Exception {
        View page = command.execute(wrapper);
        assertEquals(View.GUEST_REGISTER, page);
        verify(wrapper, times(0)).setSessionAttribute(any(SessionAttributes.class), anyString());
        verify(wrapper, times(0)).setRequestAttribute(any(RequestAttributes.class), anyString());
    }

}