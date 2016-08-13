package ua.mishkyroff.carget.commands.post;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.commands.Messages;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.dao.UsersDAO;
import ua.mishkyroff.carget.entities.User;

/**
 * A command for process POST requests with "/guest_registration" uri
 * The command validates given input parameters
 * The command sets error message if some parameters are missing or user already exists
 * If user doesn't exist create new user
 *
 * @author Anton Mishkyroff
 */
public class RegistrationCommand implements Command {
    @Override
    public JspPages execute(IRequestWrapper wrapper) {

        String firstName = wrapper.getParameter("firstName");
        String lastName = wrapper.getParameter("lastName");
        String email = wrapper.getParameter("email");
        String passport = wrapper.getParameter("passport");
        String password = wrapper.getParameter("password");
        String password_repeat = wrapper.getParameter("password_repeat");

        if (firstName == null || lastName == null || email == null || passport == null ||
                password == null || password_repeat == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.FILL_ALL_FIELDS);
            return JspPages.GUEST_REGISTER;
        }

        if (!password.equals(password_repeat)) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_PASSWORDS_ARE_NOT_EQUAL);
            return JspPages.GUEST_REGISTER;
        }
        if (!passport.matches("[A-zА-я]{2}[0-9]{6}")) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_PASSPORT_NUMBER_INCORRECT);
            return JspPages.GUEST_REGISTER;
        }
        if (!email.matches("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$")) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_EMAIL_INCORRECT);
            return JspPages.GUEST_REGISTER;
        }

        //default role for new users is USER
        User user = new User(firstName, lastName, passport, email, false, password);
        DAOFactory daoFactory = wrapper.getDAOFactory();
        UsersDAO usersDAO = daoFactory.getUsersDAO();
        if (usersDAO.add(user)) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.USER_REGISTERED_SUCCESSFULLY);
        } else {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_USER_ALREADY_EXISTS);
        }
        return JspPages.INDEX;

    }
}
