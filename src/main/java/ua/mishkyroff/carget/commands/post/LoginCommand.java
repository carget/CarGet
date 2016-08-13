package ua.mishkyroff.carget.commands.post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.commands.Messages;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.dao.UsersDAO;
import ua.mishkyroff.carget.entities.User;
import ua.mishkyroff.carget.entities.UserRole;

/**
 * A command for process POST requests with "/guest_login" uri
 * The command validates user by login (email) and password
 * The command sets error message if user invalid
 * or fills session attributes (user_name, user_ id, user_role) if user valid
 * Always forwards to index
 *
 * @author Anton Mishkyroff
 */
public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public JspPages execute(IRequestWrapper wrapper) {

        String email = wrapper.getParameter("email");
        String password = wrapper.getParameter("password");
        if (email == null || password == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_INVALID_USER);
            return JspPages.INDEX;
        }
        DAOFactory daoFactory = wrapper.getDAOFactory();
        UsersDAO userDao = daoFactory.getUsersDAO();
        User user = userDao.getUserByEmail(email);
        if (user != null && password.equals(user.getPassword())) { //valid user
            LOGGER.debug("Valid user");
            wrapper.setSessionAttribute(SessionAttributes.USER_ROLE,
                    (user.getAdmin()) ? UserRole.ADMIN : UserRole.USER);
            wrapper.setSessionAttribute(SessionAttributes.USER_NAME,
                    user.getFirstName() + " " + user.getLastName());
            wrapper.setSessionAttribute(SessionAttributes.USER_ID, user.getId());
        } else { //invalid user set error message
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_INVALID_USER);
        }
        return JspPages.INDEX;
    }
}
