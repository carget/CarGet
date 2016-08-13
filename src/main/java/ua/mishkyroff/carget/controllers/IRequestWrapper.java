package ua.mishkyroff.carget.controllers;

import ua.mishkyroff.carget.dao.DAOFactory;

/**
 * Interface {@code IRequestWrapper} used for testing purposes
 * Used like facade for HttpServletRequest
 * Also used for testing purposes
 *
 * @author Anton Mishkyroff
 */
public interface IRequestWrapper {

    String getParameter(String key);

    Object getRequestAttribute(RequestAttributes name);

    void setRequestAttribute(RequestAttributes name, Object attribute);

    String getMethod();

    String getPathInfo();

    Object getSessionAttribute(SessionAttributes attributeName);

    void setSessionAttribute(SessionAttributes attributeName, Object attribute);

    void invalidateSession();

    String getQueryString();

    void removeSessionAttribute(SessionAttributes key);

    String getHeader(String name);

    DAOFactory getDAOFactory();
}
