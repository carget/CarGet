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
import ua.mishkyroff.carget.entities.User;
import ua.mishkyroff.carget.entities.UserRole;
import ua.mishkyroff.carget.model.commands.Command;

import static org.junit.Assert.assertEquals;
import static ua.mishkyroff.carget.model.Messages.ERROR_INVALID_USER;

/**
 * Test class for LoginCommand.class
 *
 * @author Anton Mishkyroff
 */
public class LoginCommandTest extends Mockito {

    private static IRequestWrapper wrapper;
    private static DAOManager daoManager;
    private static Command command;
    private static User user;

    @Before
    public void setUp() throws Exception {
        wrapper = mock(RequestWrapper.class);
        daoManager = mock(DAOManager.class);
        command = new LoginCommand();
        user = new User("admin", "Adminov", "RR123456", "admin@mail.com", true, "root");
        user.setId(5);
    }

    @After
    public void tearDown() throws Exception {
        wrapper = null;
        daoManager = null;
        command = null;
        user = null;
    }

    @Test
    public void nullLoginPassword() throws Exception {
        when(wrapper.getParameter(anyString())).thenReturn(null);
        when(wrapper.getParameter(anyString())).thenReturn(null);

        View page = command.execute(wrapper);

        assertEquals(View.INDEX, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ERROR_INVALID_USER);
    }

    @Test
    public void correctLogin() throws Exception {
        String email = "admin@mail.com";
        when(wrapper.getParameter("email")).thenReturn(email);
        when(wrapper.getParameter("password")).thenReturn("root");
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.openExecuteAndClose(any(DAOCommand.class))).thenReturn(user);

        View page = command.execute(wrapper);

        assertEquals(View.INDEX, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.USER_ROLE, UserRole.ADMIN);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.USER_NAME, "admin Adminov");
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.USER_ID, user.getId());

    }

    @Test
    public void incorrectLogin() throws Exception {
        String email = "false_admin@mail.com";
        when(wrapper.getParameter("email")).thenReturn(email);
        when(wrapper.getParameter("password")).thenReturn("rOOt");
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.openExecuteAndClose(any(DAOCommand.class))).thenReturn(user);

        View page = command.execute(wrapper);

        assertEquals(View.INDEX, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ERROR_INVALID_USER);

    }
}