package ua.mishkyroff.carget.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.entities.Brand;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.entities.FuelType;
import ua.mishkyroff.carget.entities.Model;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class {@code CarsDAO} used for CRUD and other useful DB operations with Car entity
 *
 * @author Anton Mishkyroff
 */
public class CarsDAO {
    private final DataSource ds;
    private static final Logger LOGGER = LogManager.getLogger("toConsole");
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("sql_statements");

    CarsDAO(DataSource ds) {
        this.ds = ds;
    }

    public Car getCarById(int id) {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(BUNDLE.getString("GET_CAR_BY_ID"))) {

            statement.setString(1, Integer.valueOf(id).toString());
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            } else {
                return getCarFromResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.error("get car by id SQL error " + e);
            return null;
        }
    }

    @Deprecated
    public List<Car> getAllCars() {

        List<Car> cars = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(BUNDLE.getString("GET_ALL_CARS"));
            while (rs.next()) {
                cars.add(getCarFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("get all cars SQL error " + e);
            return null;
        }
        return cars;
    }

    public List<Car> filterAndGetCars(CarFilter carFilter) {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(BUNDLE.getString("GET_CARS_FILTER"))) {
            statement.setDate(1, carFilter.getSelectedStartDate());
            statement.setDate(2, carFilter.getSelectedEndDate());
            statement.setDate(3, carFilter.getSelectedStartDate());
            statement.setDate(4, carFilter.getSelectedEndDate());
            statement.setString(5, (carFilter.getSelectedBrandId() == -1) ? "%" :
                    carFilter.getSelectedBrandId().toString());
            statement.setString(6, (carFilter.getSelectedYear() == -1) ? "%" :
                    carFilter.getSelectedYear().toString());
            statement.setString(7, (carFilter.getSelectedAutomat() == -1) ? "%" :
                    carFilter.getSelectedAutomat().toString());
            statement.setString(8, (carFilter.getSelectedCondition() == -1) ? "%" :
                    carFilter.getSelectedCondition().toString());
            statement.setString(9, (carFilter.getSelectedFuelType() == FuelType.ALL) ? "%" :
                    carFilter.getSelectedFuelType().toString());
            LOGGER.debug(" filter car SQL " + statement);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                cars.add(getCarFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("get all filtered cars SQL error " + e);
            return null;
        }
        return cars;
    }

    private Car getCarFromResultSet(ResultSet rs) throws SQLException {
        Car car = new Car();
        Model model = new Model();
        Brand brand = new Brand();
        brand.setIdBrand(rs.getInt("brands.brand_id"));
        brand.setBrandAbbr(rs.getString("brands.brand_name"));
        brand.setFullName(rs.getString("brands.brand_full_name"));
        model.setBrand(brand);
        model.setIdModel(rs.getInt("models.model_id"));
        model.setClassName(rs.getString("models.class_name"));
        model.setModelName(rs.getString("models.model_name"));
        model.setDoorsQty(rs.getInt("models.doors_qty"));
        model.setAutomat(rs.getBoolean("models.automat"));
        model.setPower(rs.getDouble("models.power"));
        model.setCondition(rs.getBoolean("models.conditioning"));
        car.setIdCar(rs.getInt("cars.car_id"));
        car.setModel(model);
        car.setYear(rs.getInt("cars.year"));
        car.setFuelType(FuelType.valueOf(rs.getString("cars.fuel_type").toUpperCase()));
        car.setPricePerDay(rs.getDouble("cars.price_day"));
        return car;
    }

    public List<Integer> getAllYears() {
        List<Integer> years = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             Statement statement =
                     connection.createStatement()) {
            ResultSet rs = statement.executeQuery(BUNDLE.getString("GET_ALL_CAR_YEARS"));
            while (rs.next()) {
                years.add(rs.getInt("year"));
            }
            return years;
        } catch (SQLException e) {
            LOGGER.error("get all cars' years SQL error " + e);
            return null;
        }
    }

    @Deprecated
    private String toMySQLDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public boolean checkAvailability(Integer carId, Date start, Date end) {

        try (Connection connection = ds.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(BUNDLE.getString("GET_CARS_AVAILABILITY"))) {

            statement.setDate(1, start);
            statement.setDate(2, end);
            statement.setDate(3, start);
            statement.setDate(4, end);
            statement.setString(5, carId.toString());
            LOGGER.debug("check availability car by id and start and date = " + statement);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            LOGGER.error("get car by id SQL error " + e);
            return false;
        }

    }

    public List<FuelType> getAllFuelTypes() {
        List<FuelType> fuelTypes = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(BUNDLE.getString("GET_ALL_CAR_FUEL_TYPES"));
            while (rs.next()) {
                fuelTypes.add(FuelType.valueOf(rs.getString("fuel_type")));
            }
            return fuelTypes;
        } catch (SQLException e) {
            LOGGER.error("get all cars' fuel types SQL error " + e);
            return null;
        }
    }
}
