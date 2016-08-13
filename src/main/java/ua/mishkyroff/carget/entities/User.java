package ua.mishkyroff.carget.entities;

/**
 * A User entity stores information about user's information
 * The entity has its representation in the database
 *
 * @author Anton Mishkyroff
 */
public class User {
    private int id;
    private final String firstName;
    private final String lastName;
    private final String passport;
    private final String email;
    private String password;
    private Boolean isAdmin;

    public User(String firstName, String lastName, String passport, String email, Boolean isAdmin, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passport = passport;
        this.email = email;
        this.isAdmin = isAdmin;
        this.password = password;
    }

    public User(int id, String firstName, String lastName, String passport, String email, Boolean
            isAdmin, String password) {
        this(firstName, lastName, passport, email, isAdmin, password);
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassport() {
        return passport;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
