package ua.mishkyroff.carget.model.commands.get;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestWrapper;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.model.commands.Command;

import static org.junit.Assert.assertEquals;

/**
 * //TODO add comments
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
    }

}