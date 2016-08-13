package ua.mishkyroff.carget.entities;

/**
 * A Car entity stores information about car's information
 * The entity has its representation in the database
 *
 * @author Anton Mishkyroff
 */
public class Car {
    private int idCar;
    private final Model model;
    private final int year;
    private final FuelType fuelType;
    private double pricePerDay;

    public Car(Model model, int year, FuelType fuelType, double pricePerDay) {
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.pricePerDay = pricePerDay;
    }

    public Car(int idCar, Model model, int year, FuelType fuelType, double pricePerDay) {
        this(model, year, fuelType, pricePerDay);
        this.idCar =idCar;
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

    public FuelType getFuelType() {
        return fuelType;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
