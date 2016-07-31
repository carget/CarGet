package ua.mishkyroff.carget.controllers;

/**
 * Class {@code SessionAttributes} used for storing common session attributes names
 * Used for standardization purposes
 *
 * @author Anton Mishkyroff
 */
public enum SessionAttributes {
    LOCALE("locale"),
    USER_ID("user_id"),
    USER_NAME("user_name"),
    USER_ROLE("user_role"),
    MESSAGE("error"),
    CAR("car"),
    CAR_FILTER("car_filter"),
    CARS("cars"),
    ORDERS("orders"),
    ORDER("order"),
    START_DATE("start_date"),
    END_DATE("end_date");

    private String name;

    SessionAttributes(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
