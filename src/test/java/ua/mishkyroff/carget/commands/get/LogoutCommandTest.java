package ua.mishkyroff.carget.commands.get;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestWrapper;

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

        JspPages page = command.execute(wrapper);
        assertEquals(page, JspPages.INDEX);
        verify(wrapper,times(1)).invalidateSession();

    }

}