package ua.mishkyroff.carget.commands.post;

import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
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
    public String execute(IRequestWrapper wrapper) {

        String firstName = wrapper.getParameter("firstName");
        String lastName = wrapper.getParameter("lastName");
        String email = wrapper.getParameter("email");
        String passport = wrapper.getParameter("passport");
        String password = wrapper.getParameter("password");
        String password_repeat = wrapper.getParameter("password_repeat");

        if (firstName == null ||
                lastName == null ||
                email == null ||
                passport == null ||
                password == null ||
                password_repeat == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, FILL_ALL_FIELDS);
            return REGISTER;
        }

        if (!password.equals(password_repeat)) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, ERROR_PASSWORDS_ARE_NOT_EQUAL);
            return REGISTER;
        }
        if (!passport.matches("[A-zА-я]{2}[0-9]{6}")) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, ERROR_PASPORT_NUMBER_INCORRECT);
            return REGISTER;
        }
        if (!email.matches("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$")) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, ERROR_EMAIL_INCORRECT);
            return REGISTER;
        }

        User u = new User();
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setPassport(passport);
        u.setAdmin(false);  //default role for new users is USER
        u.setPassword(password);

        UsersDAO usersDAO = DAOFactory.getInstance().getUsersDAO();
        if (!usersDAO.add(u)) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, ERROR_USER_ALREADY_EXISTS);
        } else {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, ERROR_USER_REGISTERED_SUCCESSFULLY);
        }
        return INDEX;

    }
}
