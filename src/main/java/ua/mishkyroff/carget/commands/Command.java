package ua.mishkyroff.carget.commands;

import ua.mishkyroff.carget.controllers.IRequestWrapper;

/**
 * This singleton class used to retrieve corresponding {@code Command} object for given uri
 * It also stores error and jsp path constants for standardization
 *
 * @author Anton Mishkyroff
 * @see AbstractActionFactory
 */

public interface Command {
    String INDEX = "index";
    String REGISTER = "guest_register";
    String CHOOSE_AUTO = "choose_auto";
    String ERROR_404 = "404";
    String PREPARE_ORDER = "user_prepare_order";
    String MY_ORDERS = "user_my_orders";
    String CAR_INFO = "car_info";
    String NEW_ORDERS = "admin_new_orders";
    String APPROVED_ORDERS = "admin_approved_orders";
    String COMPLETED_ORDERS = "admin_completed_orders";
    String REJECTED_ORDERS = "admin_rejected_orders";
    String RETURN_CAR = "admin_return_car";
    String ERROR_USER_ROLE = "error_role";

    String ERROR_YOU_MUST_BE_ADMIN = "YOU_MUST_BE_ADMIN";
    String ERROR_YOU_MUST_BE_USER = "YOU_MUST_BE_USER";
    String ERROR_INVALID_USER = "ERROR_INVALID_USER";
    String ERROR_BEGIN_DATE_GREATER_OR_EQUALS = "BEGIN_DATE_GREATER_OR_EQUALS";
    String ERROR_CAR_IS_NOT_AVAILABLE_FOR_THIS_DATES = "CAR_IS_NOT_AVAILABLE_FOR_THIS_DATES";
    String ERROR_CHOOSING_CAR = "ERROR_CHOOSING_CAR";
    String ERROR_USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    String ERROR_USER_REGISTERED_SUCCESSFULLY = "USER_REGISTERED_SUCCESSFULLY";
    String ERROR_START_DATE_MUST_BE_GE_TODAY = "START_DATE_MUST_BE_GE_TODAY";
    String ERROR_END_DATE_MUST_BE_GT_START = "END_DATE_MUST_BE_GT_START";
    String ERROR_YOU_SHOULD_LOGOUT_TO_REGISTER = "YOU_SHOULD_LOGOUT_TO_REGISTER";
    String ERROR_YOU_MUST_BE_GUEST = "ERROR_YOU_MUST_BE_GUEST";
    String ORDER_ADDED_SUCCESSFULLY = "ORDER_ADDED_SUCCESSFULLY";
    String FIRSTLY_CHOOSE_AUTO = "FIRSTLY_CHOOSE_AUTO";
    String FIRSTLY_CHOOSE_ORDER = "CHOOSE_ORDER_TO_COMPLETE";
    String ENTER_REASON_FOR_REJECT = "ENTER_REASON_FOR_REJECT";
    String FILL_ALL_FIELDS = "FILL_ALL_FIELDS";
    String ERROR_PASSWORDS_ARE_NOT_EQUAL = "PASSWORDS_ARE_NOT_EQUAL";
    String ERROR_PASPORT_NUMBER_INCORRECT = "PASPORT_NUMBER_INCORRECT";
    String ERROR_EMAIL_INCORRECT = "EMAIL_INCORRECT";

    /**
     * Processes a request by retrieving all necessary parameters from it,
     * validates this params, sets error message if there are some problems with params
     * Business logic layer is here
     */
    String execute(IRequestWrapper wrapper);

}
