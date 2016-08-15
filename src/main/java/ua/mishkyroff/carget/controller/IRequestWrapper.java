package ua.mishkyroff.carget.controller;

import ua.mishkyroff.carget.dao.AbstractDAOFactory;

/**
 * Interface {@code IRequestWrapper}
 * This is a proxy for HttpServletRequest
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

    AbstractDAOFactory getDAOFactory();
}
