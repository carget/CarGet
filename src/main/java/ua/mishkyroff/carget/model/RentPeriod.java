package ua.mishkyroff.carget.model;

import ua.mishkyroff.carget.model.commands.nonidempotent.ProcessOrderCommand;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This class represents rental period. It stores start and end date values.
 * Also it useful for validation selected dates and calculate days difference
 *
 * @author Anton Mishkyroff
 * @see CarFilter
 * @see ProcessOrderCommand
 */
public class RentPeriod {
    private LocalDate startDate;
    private LocalDate endDate;
    private String error;
    private boolean haveUnreadError;

    {
        error = null;
        haveUnreadError = false;
    }

    public RentPeriod() {
        LocalDate today = LocalDate.now();
        this.startDate = today;
        this.endDate = today.plusDays(1);
    }

    public RentPeriod(LocalDate startDate, LocalDate endDate) {
        setStartDate(startDate);
        setEndDate(endDate);
    }

    /**
     * Validates passed date and set error message if there are some problems with end date value
     * If end date is wrong then corrects it
     *
     * @param startDate - date to check, correct and store
     * @return - String that represents error message if some error present or
     * null if there is no error
     */
    public void setStartDate(LocalDate startDate) {
        // IF start date < today THEN set Error and set 'today' as correct start date
        LocalDate today = LocalDate.now();
        if (startDate.compareTo(today) < 0) {
            this.startDate = today;
            setErrorMessage(Messages.ERROR_START_DATE_MUST_BE_GE_TODAY);
        } else {
            this.startDate = startDate;
        }
    }

    /**
     * Validates passed date and set error message if there are some problems with end date value
     * If end date is wrong then corrects it
     *
     * @param endDate - date to check, correct and store
     * @return - String that represents error message if some error present or
     * null if there is no error
     */
    public void setEndDate(LocalDate endDate) {
        // IF end date <= start date THEN return Error and correct date
        if (endDate.compareTo(this.startDate) <= 0) {
            this.endDate = this.startDate.plusDays(1);
            setErrorMessage(Messages.ERROR_END_DATE_MUST_BE_GT_START);
        } else {
            this.endDate = endDate;
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Helper method to set error message
     * If previous error hasn't been read then new error will be ignored
     *
     * @param message - error message
     */
    private void setErrorMessage(String message) {
        if (!haveUnreadError) {
            error = message;
            haveUnreadError = true;
        }
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

    private void setError(String error) {
        this.error = error;
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

    /**
     * Calculate difference in days between start and end dates
     *
     * @return - difference in days
     */
    public long getDiffDays() {
        return DAYS.between(startDate, endDate);
    }

    /**
     * Calculates total rent price
     *
     * @param pricePerDay - given rent price per day
     * @return - total rent price
     */
    public BigDecimal getTotalRent(BigDecimal pricePerDay) {
        return pricePerDay.multiply(new BigDecimal(getDiffDays()));
    }
}
