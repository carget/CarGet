package ua.mishkyroff.carget.controllers;

/**
 * Interface {@code IRequestWrapper} used for testing purposes
 * Used like facade for HttpServletRequest
 * Also used for testing purposes
 *
 * @author Anton Mishkyroff
 */
public interface IRequestWrapper {
    //TODO reqrite to ENUM session attributes

    String getParameter(String key);

    String getMethod();

    String getPathInfo();

    Object getSessionAttribute(SessionAttributes attributeName);

    void setSessionAttribute(SessionAttributes attributeName, Object attribute);

    void invalidateSession();

    String getQueryString();

    void removeSessionAttribute(SessionAttributes key);

    String getHeader(String name);
}
