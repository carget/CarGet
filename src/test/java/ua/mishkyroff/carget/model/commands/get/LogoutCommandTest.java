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
public class LogoutCommandTest extends Mockito{
    private static IRequestWrapper wrapper;
    private static Command command;
    @Before
    public void setUp() throws Exception {
        wrapper = mock(RequestWrapper.class);
        command= new LogoutCommand();
    }

    @After
    public void tearDown() throws Exception {
        command = null;
        wrapper = null;
    }

    @Test
    public void execute() throws Exception {

        View page = command.execute(wrapper);
        assertEquals(View.INDEX, page);
        verify(wrapper,times(1)).invalidateSession();

    }

}