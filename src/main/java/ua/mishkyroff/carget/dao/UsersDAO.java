package ua.mishkyroff.carget.dao;

import ua.mishkyroff.carget.entities.User;
import ua.mishkyroff.carget.entities.UserRole;

/**
 * Interface for all common operation with user table in the database
 *
 * @author Anton Mishkyroff
 */
public interface UsersDAO {
    UserRole getUserRole(User user);

    boolean add(User user);

    User getUserByEmail(String email);

    User getUserById(Integer id);
}
