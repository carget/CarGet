package ua.mishkyroff.carget.entities;

import java.math.BigDecimal;

/**
 * A Car entity stores information about car's information
 * The entity has its representation in the database
 *
 * @author Anton Mishkyroff
 */
public class Car {
    public static final int PETROL = 0;
    public static final int GASOLINE = 1;
    public static final int DISEL = 2;
    public static final int ALL = 3;
    private int idCar;
    private final Model model;
    private final int year;
    private final Integer fuelType;
    private BigDecimal pricePerDay;

    public Car(Model model, int year, Integer fuelType, BigDecimal pricePerDay) {
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.pricePerDay = pricePerDay;
    }

    public Car(int idCar, Model model, int year, Integer fuelType, BigDecimal pricePerDay) {
        this(model, year, fuelType, pricePerDay);
        this.idCar = idCar;
    }

    @Override
    public String toString() {
        return "Car id=" + idCar + " year=" + year + " fuelType=" + fuelType.toString() + " price" +
                " per day=" + pricePerDay + " Model=" + model;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public Model getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public Integer getFuelType() {
        return fuelType;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public static String fuelTypeToString(Integer fuelType) {
        switch (fuelType) {
            case 0:
                return "PETROL";
            case 1:
                return "GASOLINE";
            case 2:
                return "DISEL";
            case 3:
                return "ALL";
            default:
                return "UNKNOWN_FUEL_TYPE";
        }
    }

    public String getFuelTypeString() {
        return Car.fuelTypeToString(fuelType);
    }
}
