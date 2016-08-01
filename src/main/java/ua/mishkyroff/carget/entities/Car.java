package ua.mishkyroff.carget.entities;

/**
 * A Car entity stores information about car's information
 * The entity has its representation in the database
 *
 * @author Anton Mishkyroff
 */
public class Car {
    private int idCar;
    private Model model;
    private int year;
    private FuelType fuelType;
    private double pricePerDay;

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

    public void setModel(Model model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
