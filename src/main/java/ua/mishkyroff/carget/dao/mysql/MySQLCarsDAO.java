package ua.mishkyroff.carget.dao.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.dao.CarsDAO;
import ua.mishkyroff.carget.entities.Brand;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.entities.Model;
import ua.mishkyroff.carget.model.CarFilter;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class {@code CarsDAO} used for CRUD and other useful DB operations with Car entity
 *
 * @author Anton Mishkyroff
 */
public class MySQLCarsDAO implements CarsDAO {
    private final Connection connection;
    private static final Logger LOGGER = LogManager.getLogger("toConsole");
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("sql_statements");

    public MySQLCarsDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Car getCarById(int id) {
        try (PreparedStatement statement = connection.prepareStatement(BUNDLE.getString("GET_CAR_BY_ID"))) {
            statement.setString(1, Integer.valueOf(id).toString());
            ResultSet rs = statement.executeQuery();
            Car car = null;
            if (rs.next()) {
                car = getCarFromResultSet(rs);
            }
            rs.close();
            return car;
        } catch (SQLException e) {
            LOGGER.error("get car by id SQL error " + e);
            return null;
        }
    }

    @Override
    @Deprecated
    public List<Car> getAllCars() {

        List<Car> cars = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(BUNDLE.getString("GET_ALL_CARS"))) {
            while (rs.next()) {
                cars.add(getCarFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("get all cars SQL error " + e);
            return null;
        }
        return cars;
    }

    @Override
    public List<Car> filterAndGetCars(CarFilter carFilter) {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement =
                     connection.prepareStatement(BUNDLE.getString("GET_CARS_FILTER"))) {
            statement.setDate(1, Date.valueOf(carFilter.getSelectedStartDate()));
            statement.setDate(2, Date.valueOf(carFilter.getSelectedEndDate()));
            statement.setDate(3, Date.valueOf(carFilter.getSelectedStartDate()));
            statement.setDate(4, Date.valueOf(carFilter.getSelectedEndDate()));
            statement.setString(5, (carFilter.getSelectedBrandId() == -1) ? "%" :
                    carFilter.getSelectedBrandId().toString());
            statement.setString(6, (carFilter.getSelectedYear() == -1) ? "%" :
                    carFilter.getSelectedYear().toString());
            statement.setString(7, (carFilter.getSelectedAutomat() == -1) ? "%" :
                    carFilter.getSelectedAutomat().toString());
            statement.setString(8, (carFilter.getSelectedCondition() == -1) ? "%" :
                    carFilter.getSelectedCondition().toString());
            statement.setString(9, (carFilter.getSelectedFuelType() == Car.ALL) ? "%" :
                    carFilter.getSelectedFuelType().toString());
            statement.setInt(10, carFilter.getSelectedLowPrice());
            statement.setInt(11, carFilter.getSelectedHiPrice());
            LOGGER.debug(" filter car SQL " + statement);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                cars.add(getCarFromResultSet(rs));
            }
            rs.close();
        } catch (SQLException e) {
            LOGGER.error("get all filtered cars SQL error " + e);
            return null;
        }
        return cars;
    }

    private Car getCarFromResultSet(ResultSet rs) throws SQLException {
        Brand brand = new Brand(
                rs.getInt("brands.brand_id"),
                rs.getString("brands.brand_name"),
                rs.getString("brands.brand_full_name")
        );
        Model model = new Model(
                rs.getInt("models.model_id"),
                brand,
                rs.getString("models.class_name"),
                rs.getString("models.model_name"),
                rs.getInt("models.doors_qty"),
                rs.getBoolean("models.automat"),
                rs.getDouble("models.power"),
                rs.getBoolean("models.conditioning"),
                rs.getString("models.img")
        );
        Car car = new Car(
                rs.getInt("cars.car_id"),
                model,
                rs.getInt("cars.year"),
                rs.getInt("cars.fuel_type"),
                rs.getBigDecimal("cars.price_day")
        );
        return car;
    }

    @Override
    public List<Integer> getAllYears() {
        List<Integer> years = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(BUNDLE.getString("GET_ALL_CAR_YEARS"))) {
            while (rs.next()) {
                years.add(rs.getInt("year"));
            }
            return years;
        } catch (SQLException e) {
            LOGGER.error("get all cars' years SQL error " + e);
            return null;
        }
    }

    @Override
    public boolean checkAvailability(Integer carId, LocalDate start, LocalDate end) {

        try (PreparedStatement statement =
                     connection.prepareStatement(BUNDLE.getString("GET_CARS_AVAILABILITY"))) {

            statement.setDate(1, Date.valueOf(start));
            statement.setDate(2, Date.valueOf(end));
            statement.setDate(3, Date.valueOf(start));
            statement.setDate(4, Date.valueOf(end));
            statement.setString(5, carId.toString());
            LOGGER.debug("check availability car by id and start and date = " + statement);
            ResultSet rs = statement.executeQuery();
            boolean result = rs.next();
            rs.close();
            return result;
        } catch (SQLException e) {
            LOGGER.error("get car by id SQL error " + e);
            return false;
        }
    }

    @Override
    public List<Integer> getAllFuelTypes() {
        List<Integer> fuelTypes = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(BUNDLE.getString("GET_ALL_CAR_FUEL_TYPES"))) {
            while (rs.next()) {
                fuelTypes.add(rs.getInt("fuel_type"));
            }
            return fuelTypes;
        } catch (SQLException e) {
            LOGGER.error("get all cars' fuel types SQL error " + e);
            return null;
        }
    }

}
