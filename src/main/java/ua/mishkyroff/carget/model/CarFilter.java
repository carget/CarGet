package ua.mishkyroff.carget.model;

import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.dao.CarsDAO;
import ua.mishkyroff.carget.entities.Brand;
import ua.mishkyroff.carget.entities.Car;

import java.time.LocalDate;
import java.util.List;

/**
 * Class {@code CarFilter} used to store and validate filter's parameters
 *
 * @author Anton Mishkyroff
 * @see CarsDAO#filterAndGetCars(CarFilter)
 * @see RentPeriod
 */
public class CarFilter {
    private List<Brand> brands;
    private List<Integer> years;
    private List<Integer> fuelTypes;
    private Integer selectedBrandId;
    private Integer selectedYear;
    private Integer selectedCondition;
    private Integer selectedAutomat;
    private Integer selectedFuelType;
    private RentPeriod period;
    private String error;
    private boolean haveUnreadError;
    private int selectedLowPrice;
    private int selectedHiPrice;

    /**
     * Create filter object with default parameters for car filtration
     */
    public CarFilter(AbstractDAOFactory daoFactory) {
        //default filtering params
        this.brands = daoFactory.getBrandsDAO().getAllBrands();
        this.years = daoFactory.getCarsDAO().getAllYears();
        this.fuelTypes = daoFactory.getCarsDAO().getAllFuelTypes();
        LocalDate today = LocalDate.now(); //set today
        this.period = new RentPeriod(LocalDate.now(), (today.plusDays(1)));
        // '-1' value means any filtering value
        this.selectedBrandId = -1;
        this.selectedYear = -1;
        this.selectedCondition = -1;
        this.selectedAutomat = -1;
        this.selectedFuelType = Car.ALL;
        this.error = "";
        this.haveUnreadError = false;
        this.selectedLowPrice = 0;
        this.selectedHiPrice = 2000;
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

    public Integer getSelectedFuelType() {
        return selectedFuelType;
    }

    public void setSelectedFuelType(String fuelType) {
        if (fuelType != null) {
            this.selectedFuelType = Integer.valueOf(fuelType);
        }
    }

    public LocalDate getSelectedStartDate() {
        return period.getStartDate();
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
            LocalDate startDate = LocalDate.parse(startDateString);
            period.setStartDate(startDate);
            if (period.haveUnreadError()) {
                setErrorMessage(period.getError());
            }
        }
    }

    public LocalDate getSelectedEndDate() {
        return period.getEndDate();
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
            period.setEndDate(endDate);
            if (period.haveUnreadError()) {
                setErrorMessage(period.getError());
            }
        }
    }

    public List<Integer> getFuelTypes() {
        return fuelTypes;
    }

    /**
     * Gets error message and reset error flag
     *
     * @return - error message
     */
    public String getError() {
        if (haveUnreadError) {
            haveUnreadError = false;
            return error;
        }
        return null;
    }

    /**
     * The method returns error flag. The flag will be reset after getError() method call
     *
     * @return - flag is true if unread error exists
     * @see #getError()
     */
    public boolean haveUnreadError() {
        return haveUnreadError;
    }

    public int getSelectedLowPrice() {
        return selectedLowPrice;
    }

    /**
     * Check, correct (if necessary) and se lower price value
     *
     * @param selectedLowPrice
     */
    public void setSelectedLowPrice(String selectedLowPrice) {
        if (selectedLowPrice != null) {
            this.selectedLowPrice = Integer.parseInt(selectedLowPrice);
            this.selectedLowPrice = this.selectedLowPrice < 0 ? 0 : this.selectedLowPrice;
        }
    }

    public int getSelectedHiPrice() {
        return selectedHiPrice;
    }

    /**
     * Check, correct (if necessary) and se high price value.
     * Set error message if price value has been corrected
     *
     * @param selectedHiPrice
     */
    public void setSelectedHiPrice(String selectedHiPrice) {
        if (selectedHiPrice != null) {
            this.selectedHiPrice = Integer.parseInt(selectedHiPrice);
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
