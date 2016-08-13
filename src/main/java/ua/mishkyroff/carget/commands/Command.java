package ua.mishkyroff.carget.commands;

import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;

/**
 * Interface to retrieve corresponding {@code Command} object for given uri
 *
 * @author Anton Mishkyroff
 * @see CommandFactory
 */

public interface Command{

    /**
     * Processes a request by retrieving all necessary parameters from it,
     * validates this params, sets error message if there are some problems with params
     * Business logic layer is here
     */
    JspPages execute(IRequestWrapper wrapper);

}
