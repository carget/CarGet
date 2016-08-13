package ua.mishkyroff.carget.commands.post;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestWrapper;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.dao.UsersDAO;
import ua.mishkyroff.carget.entities.User;
import ua.mishkyroff.carget.entities.UserRole;

import static org.junit.Assert.assertEquals;
import static ua.mishkyroff.carget.commands.Messages.ERROR_INVALID_USER;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public class LoginCommandTest extends Mockito {

    private static IRequestWrapper wrapper;
    private static DAOFactory daoFactory;
    private static UsersDAO usersDAO;
    private static Command command;
    private static User user;

    @Before
    public void setUp() throws Exception {
        wrapper = mock(RequestWrapper.class);
        daoFactory = mock(DAOFactory.class);
        usersDAO = mock(UsersDAO.class);
        command = new LoginCommand();
        user = new User("admin", "Adminov", "RR123456", "admin@mail.com", true, "root");
        user.setId(5);
    }

    @After
    public void tearDown() throws Exception {
        wrapper = null;
        daoFactory = null;
        usersDAO = null;
        command = null;
        user = null;
    }

    @Test
    public void nullLoginPassword() throws Exception {
        when(wrapper.getParameter(anyString())).thenReturn(null);
        when(wrapper.getParameter(anyString())).thenReturn(null);

        JspPages page = command.execute(wrapper);

        assertEquals(page, JspPages.INDEX);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ERROR_INVALID_USER);
    }

    @Test
    public void correctLogin() throws Exception {
        String email = "admin@mail.com";
        when(wrapper.getParameter("email")).thenReturn(email);
        when(wrapper.getParameter("password")).thenReturn("root");
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getUsersDAO()).thenReturn(usersDAO);
        when(usersDAO.getUserByEmail(email)).thenReturn(user);

        JspPages page = command.execute(wrapper);

        assertEquals(page, JspPages.INDEX);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.USER_ROLE, UserRole.ADMIN);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.USER_NAME, "admin Adminov");
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.USER_ID, user.getId());

    }

    @Test
    public void incorrectLogin() throws Exception {
        String email = "false_admin@mail.com";
        when(wrapper.getParameter("email")).thenReturn(email);
        when(wrapper.getParameter("password")).thenReturn("rOOt");
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getUsersDAO()).thenReturn(usersDAO);
        when(usersDAO.getUserByEmail(email)).thenReturn(user);

        JspPages page = command.execute(wrapper);

        assertEquals(page, JspPages.INDEX);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ERROR_INVALID_USER);

    }
}