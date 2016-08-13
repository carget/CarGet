package ua.mishkyroff.carget.controllers;


import ua.mishkyroff.carget.dao.DAOFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Class {@code RequestWrapper} implements {@code IRequestWrapper}
 * Declares methods to communicate with HttpServletRequest
 * Used like facade for HttpServletRequest
 * Also used for testing purposes
 *
 * @author Anton Mishkyroff
 */
public class RequestWrapper implements IRequestWrapper {

    private HttpServletRequest request;

    public RequestWrapper(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Used to retrieve parameter from request and prevents javascript injection
     *
     * @param key - request parameter name
     * @return - null if such parameter does not exist or parameter's value
     */
    @Override
    public String getParameter(String key) {
        //prevention javascript injection
        String parameter = request.getParameter(key);
        if (parameter != null) {
            return parameter.replace("<", "&lt;").replace(">", "&gt;");
        } else {
            return null;
        }
    }

    /**
     * Retrieves attribute object stored in request container by given name
     *
     * @param name - attribute name
     * @return - attribute object
     */
    @Override
    public Object getRequestAttribute(RequestAttributes name) {
        return request.getAttribute(name.toString());
    }

    /**
     * Saves attribute to request container
     *
     * @param name      - attribute name
     * @param attribute - attribute value
     */
    @Override
    public void setRequestAttribute(RequestAttributes name, Object attribute) {
        request.setAttribute(name.toString(), attribute);
    }

    /**
     * Retrieves information about request method (POST, GET, etc)
     *
     * @return string represents request method
     */
    @Override
    public String getMethod() {
        return request.getMethod();
    }

    /**
     * Returns any extra path information associated with the URL the client sent when it made
     * this request. The extra path information follows the servlet path but precedes
     * the query string and will start with a "/" character.
     * This method returns null if there was no extra path information.
     */
    @Override
    public String getPathInfo() {
        return request.getPathInfo();
    }

    /**
     * Used to get session attribute from request
     *
     * @param attributeName - name of session attribute
     * @return - Object from session
     */
    @Override
    public Object getSessionAttribute(SessionAttributes attributeName) {
        return request.getSession(true).getAttribute(attributeName.toString());
    }

    /**
     * Stores an attribute in a session scope.
     */
    @Override
    public void setSessionAttribute(SessionAttributes attributeName, Object attribute) {
        request.getSession(true).setAttribute(attributeName.toString(), attribute);
    }

    /**
     * Invalidates current session
     */
    @Override
    public void invalidateSession() {
        request.getSession(true).invalidate();
    }

    /**
     * Returns the query string that is contained in the request URL after the path.
     * This method returns null if the URL does not have a query string.
     */
    @Override
    public String getQueryString() {
        return request.getQueryString();
    }

    /**
     * Removes attribute object from session scope by given name
     *
     * @param key - name of session attribute
     */
    @Override
    public void removeSessionAttribute(SessionAttributes key) {
        request.getSession(true).removeAttribute(key.toString());
    }

    /**
     * Returns the value of the specified request header as a String. If the request did not include
     * a header of the specified name, this method returns null. If there are multiple headers
     * with the same name, this method returns the first head in the request.
     * The header name is case insensitive. You can use this method with any request header.
     */
    @Override
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    /**
     * Retrieves DAOFactory instance from ServletContext scope
     *
     * @return - DAOFactory instance
     */
    @Override
    public DAOFactory getDAOFactory() {
        return (DAOFactory) request.getServletContext().getAttribute("DAOFactory");
    }


}
