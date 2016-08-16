package ua.mishkyroff.carget.dao;

import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.model.CarFilter;

import java.time.LocalDate;
import java.util.List;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public interface CarsDAO {
    Car getCarById(int id);

    @Deprecated
    List<Car> getAllCars();

    List<Car> filterAndGetCars(CarFilter carFilter);

    List<Integer> getAllYears();

    boolean checkAvailability(Integer carId, LocalDate start, LocalDate end);

    List<Integer> getAllFuelTypes();


}
