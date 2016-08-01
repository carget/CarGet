package ua.mishkyroff.carget.controllers;

/**
 * Class {@code RequestAttributes} used for storing common request attributes names
 * Used for standardization purposes
 *
 * @author Anton Mishkyroff
 */
public enum RequestAttributes {
    MESSAGE("error"), //TODO move from session
    CAR("car"),
    CAR_FILTER("car_filter"),
    CARS("cars"),
    ORDERS("orders"),
    ORDER("order"),
    START_DATE("start_date"),
    END_DATE("end_date");

    private String name;

    RequestAttributes(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
