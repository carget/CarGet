package ua.mishkyroff.carget.model.commands.post;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestWrapper;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.dao.UsersDAO;
import ua.mishkyroff.carget.entities.User;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.commands.Command;

import static org.junit.Assert.assertEquals;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
@RunWith(Theories.class)
public class RegistrationCommandTest extends Mockito {
    private static IRequestWrapper wrapper;
    private static Command command;
    private static AbstractDAOFactory daoFactory;
    private static UsersDAO usersDAO;
    private static User user;
    //correct parameters
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "valid@email.com";
    private static final String PASSPORT = "AA123456";  //valid passport format
    private static final String PASSWORD = "password_equals";
    private static final String PASSWORD_REPEAT = "password_equals";

    @Before
    public void setUp() throws Exception {
        wrapper = mock(RequestWrapper.class);
        command = new RegistrationCommand();
        daoFactory = mock(AbstractDAOFactory.class);
        usersDAO = mock(UsersDAO.class);

        when(wrapper.getParameter("firstName")).thenReturn(FIRST_NAME);
        when(wrapper.getParameter("lastName")).thenReturn(LAST_NAME);
        when(wrapper.getParameter("email")).thenReturn(EMAIL);
        when(wrapper.getParameter("passport")).thenReturn(PASSPORT);
        when(wrapper.getParameter("password")).thenReturn(PASSWORD);
        when(wrapper.getParameter("password_repeat")).thenReturn(PASSWORD_REPEAT);

    }

    @After
    public void tearDown() throws Exception {
        wrapper = null;
        daoFactory = null;
        usersDAO = null;
        user = null;
    }

    @Test
    public void correctParams() throws Exception {

        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getUsersDAO()).thenReturn(usersDAO);
        user = new User(FIRST_NAME, LAST_NAME, PASSPORT, EMAIL, false, PASSWORD);
        when(usersDAO.add(any(User.class))).thenReturn(true);

        View page = command.execute(wrapper);

        assertEquals(View.INDEX, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.USER_REGISTERED_SUCCESSFULLY);

    }

    @DataPoints("passportsNumbers")
    public static String[] passportsNumbers() {
        return new String[]{"AA1_23456", "ФФ123_456", "wrong", "", "123456"};
    }

    @DataPoints("emails")
    public static String[] emails() {
        return new String[]{"email1", "email2", "enail3", "", "email4", "invalidmail.com", "@",
                "@.com"};
    }

    @Theory
    public void incorrectPassportFormat(@FromDataPoints("passportsNumbers") String passportFormat)
            throws Exception {

        when(wrapper.getParameter("passport")).thenReturn(passportFormat);

        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getUsersDAO()).thenReturn(usersDAO);
        user = new User(FIRST_NAME, LAST_NAME, PASSPORT, EMAIL, false, PASSWORD);
        when(usersDAO.add(any(User.class))).thenReturn(true);

        View page = command.execute(wrapper);

        assertEquals(View.GUEST_REGISTER, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_PASSPORT_NUMBER_INCORRECT);

    }

    @Theory
    public void incorrectEmailFormat(@FromDataPoints("emails") String emailFormat) throws
            Exception {


        when(wrapper.getParameter("email")).thenReturn(emailFormat);

        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getUsersDAO()).thenReturn(usersDAO);
        user = new User(FIRST_NAME, LAST_NAME, PASSPORT, EMAIL, false, PASSWORD);
        when(usersDAO.add(any(User.class))).thenReturn(true);

        View page = command.execute(wrapper);

        assertEquals(View.GUEST_REGISTER, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_EMAIL_INCORRECT);

    }

    @Test
    public void incorrectPasswords() throws Exception {

        when(wrapper.getParameter("password_repeat")).thenReturn("incorrect_password");

        View page = command.execute(wrapper);

        assertEquals(View.GUEST_REGISTER, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_PASSWORDS_ARE_NOT_EQUAL);

    }
}