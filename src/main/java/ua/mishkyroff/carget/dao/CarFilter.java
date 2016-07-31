package ua.mishkyroff.carget.dao;

import ua.mishkyroff.carget.entities.Brand;
import ua.mishkyroff.carget.entities.FuelType;
import ua.mishkyroff.carget.utils.DateUtils;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import static ua.mishkyroff.carget.commands.Command.ERROR_END_DATE_MUST_BE_GT_START;
import static ua.mishkyroff.carget.commands.Command.ERROR_START_DATE_MUST_BE_GE_TODAY;

/**
 * Class {@code CarFilter} used to store and validate filter's parameters
 *
 * @author Anton Mishkyroff
 * @see CarsDAO#filterAndGetCars(CarFilter)
 */
public class CarFilter {
    private List<Brand> brands;
    private List<Integer> years;
    private Integer selectedBrandId;
    private Integer selectedYear;
    private Integer selectedCondition;
    private Integer selectedAutomat;
    private FuelType selectedFuelType;
    private Date selectedStartDate;
    private Date selectedEndDate;
    private List<FuelType> fuelTypes;
    private String error;
    private boolean haveUnreadedError;

    /**
     * Create default parameters for car filtration
     */
    public CarFilter() {
        //default filtering params
        this.brands = DAOFactory.getInstance().getBrandsDAO().getAllBrands();
        this.years = DAOFactory.getInstance().getCarsDAO().getAllYears();
        this.selectedBrandId = -1;
        this.selectedYear = -1;
        this.selectedCondition = -1;
        this.selectedAutomat = -1;
        this.selectedFuelType = FuelType.ALL;
        //set today
        Calendar calendar = Calendar.getInstance();
        Date today = new Date(calendar.getTimeInMillis());
        this.selectedStartDate = today;
        //today + 1
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        this.selectedEndDate = new Date(calendar.getTimeInMillis());
        this.fuelTypes = DAOFactory.getInstance().getCarsDAO().getAllFuelTypes();
        this.error = "";
        this.haveUnreadedError = false;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public List<Integer> getYears() {
        return years;
    }

    public Integer getSelectedBrandId() {
        return selectedBrandId;
    }

    public void setSelectedBrandId(String brandId) {
        if (brandId != null) {
            this.selectedBrandId = Integer.valueOf(brandId);
        }
    }

    public Integer getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(String year) {
        if (year != null) {
            this.selectedYear = Integer.valueOf(year);
        }
    }

    public Integer getSelectedCondition() {
        return selectedCondition;
    }

    public void setSelectedCondition(String condition) {
        if (condition != null) {
            this.selectedCondition = Integer.valueOf(condition);
        }
    }

    public Integer getSelectedAutomat() {
        return selectedAutomat;
    }

    public void setSelectedAutomat(String automat) {
        if (automat != null) {
            this.selectedAutomat = Integer.valueOf(automat);
        }
    }

    public FuelType getSelectedFuelType() {
        return selectedFuelType;
    }

    public void setSelectedFuelType(String fuelType) {
        if (fuelType != null) {
            this.selectedFuelType = FuelType.valueOf(fuelType);
        }
    }

    public Date getSelectedStartDate() {
        return selectedStartDate;
    }

    /**
     * Validates passed date and set error message if there are some problems with end date value
     * If end date is wrong then corrects it
     *
     * @param startDateString - date to check, correct and store
     */
    public void setSelectedStartDate(String startDateString) {
        // IF start date >= today THEN set Error and set 'today' as correct start date
        if (startDateString != null) {
            Date today = DateUtils.getTodayWithoutMillis();
            Date startDate = Date.valueOf(startDateString);
            if (startDate.compareTo(today) < 0) {
                if (!haveUnreadedError) {
                    error = ERROR_START_DATE_MUST_BE_GE_TODAY;
                    haveUnreadedError = true;
                }
                this.selectedStartDate = today;
            } else {
                this.selectedStartDate = startDate;
            }
        }
    }

    public Date getSelectedEndDate() {
        return selectedEndDate;
    }

    /**
     * Validates passed date and set error message if there are some problems with end date value
     * If end date is wrong then corrects it
     *
     * @param endDateString - date to check, correct and store
     */
    public void setSelectedEndDate(String endDateString) {
        // IF end date <= start date THEN set Error and correct date
        if (endDateString != null) {
            Date endDate = Date.valueOf(endDateString);
            //check if end > start
            if (selectedStartDate.compareTo(endDate) >= 0) {
                if (!haveUnreadedError) {
                    error = ERROR_END_DATE_MUST_BE_GT_START;
                    haveUnreadedError = true;
                }
                this.selectedEndDate = DateUtils.addOneDay(selectedStartDate);
            } else {
                this.selectedEndDate = endDate;
            }
        }
    }

    public List<FuelType> getFuelTypes() {
        return fuelTypes;
    }

    /**
     * Gets error message and reset error flag
     * @return - error message
     */
    public String getError() {
        haveUnreadedError = false;
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    /**
     * The method returns error flag. The flag will be reset after getError() method call
     *
     * @return - flag is true if unreaded error exist
     * @see #getError()
     */
    public boolean haveUnreadedError() {
        return haveUnreadedError;
    }
}
