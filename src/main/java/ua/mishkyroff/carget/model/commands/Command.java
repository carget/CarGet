package ua.mishkyroff.carget.model.commands;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.View;

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
    View execute(IRequestWrapper wrapper);

}
