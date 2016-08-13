package ua.mishkyroff.carget.commands;

import ua.mishkyroff.carget.dao.CarsDAO;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.Brand;
import ua.mishkyroff.carget.entities.FuelType;

import java.time.LocalDate;
import java.util.List;

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
    private LocalDate selectedStartDate;
    private LocalDate selectedEndDate;
    private List<FuelType> fuelTypes;
    private String error;
    private boolean haveUnreadError;
    private double selectedLowPrice;
    private double selectedHiPrice;

    /**
     * Create filter object with default parameters for car filtration
     */
    public CarFilter() {
        //default filtering params
        this.brands = DAOFactory.getInstance().getBrandsDAO().getAllBrands();
        this.years = DAOFactory.getInstance().getCarsDAO().getAllYears();
        // '-1' value means any filtering value
        this.selectedBrandId = -1;
        this.selectedYear = -1;
        this.selectedCondition = -1;
        this.selectedAutomat = -1;
        this.selectedFuelType = FuelType.ALL;
        LocalDate today = LocalDate.now(); //set today
        this.selectedStartDate = today;
        this.selectedEndDate = today.plusDays(1);
        this.fuelTypes = DAOFactory.getInstance().getCarsDAO().getAllFuelTypes();
        this.error = "";
        this.haveUnreadError = false;
        this.selectedLowPrice = 0d;
        this.selectedHiPrice = 2000d;

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

    public LocalDate getSelectedStartDate() {
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
            LocalDate today = LocalDate.now();
            LocalDate startDate = LocalDate.parse(startDateString);
            if (startDate.compareTo(today) < 0) {
                setErrorMessage(Messages.ERROR_START_DATE_MUST_BE_GE_TODAY);
                this.selectedStartDate = today;
            } else {
                this.selectedStartDate = startDate;
            }
        }
    }

    public LocalDate getSelectedEndDate() {
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
            LocalDate endDate = LocalDate.parse(endDateString);
            //check if end > start
            if (selectedStartDate.compareTo(endDate) >= 0) {
                setErrorMessage(Messages.ERROR_END_DATE_MUST_BE_GT_START);
                this.selectedEndDate = selectedStartDate.plusDays(1);
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
     *
     * @return - error message
     */
    public String getError() {
        haveUnreadError = false;
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
    public boolean haveUnreadError() {
        return haveUnreadError;
    }

    public double getSelectedLowPrice() {
        return selectedLowPrice;
    }

    public void setSelectedLowPrice(String selectedLowPrice) {
        if (selectedLowPrice != null) {
            this.selectedLowPrice = Double.parseDouble(selectedLowPrice);
            this.selectedLowPrice = this.selectedLowPrice < 0 ? 0 : this.selectedLowPrice;
        }

    }

    public double getSelectedHiPrice() {
        return selectedHiPrice;
    }

    public void setSelectedHiPrice(String selectedHiPrice) {
        if (selectedHiPrice != null) {
            this.selectedHiPrice = Double.parseDouble(selectedHiPrice);
            if (this.selectedHiPrice < selectedLowPrice) {
                setErrorMessage(Messages.ERROR_HI_PRICE_MUST_BE_GE_LOW_PRICE);
                this.selectedHiPrice = selectedLowPrice;
            }
        }
    }

    /**
     * Helper method to set filter error message
     *
     * @param message - error message
     */
    private void setErrorMessage(String message) {
        if (!haveUnreadError) {
            error = message;
            haveUnreadError = true;
        }
    }
}
