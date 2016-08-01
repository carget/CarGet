package ua.mishkyroff.carget.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Util class for common operation with dates
 *
 * @author Anton Mishkyroff
 */
public final class DateUtils {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    /**
     * Parses String date to java.sql.date
     *
     * @param date - strin representation of date
     * @return - java.sql.Date object vith value of the given date
     */
    public static Date toSqlDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date result = null;
        try {
            result = dateFormat.parse(date);
        } catch (ParseException e) {
            LOGGER.error("Parse date error");
        }
        return new java.sql.Date(result.getTime());
    }

    /**
     * Calculates difference in days between two given days
     *
     * @param startDate - start date
     * @param endDate - end date
     * @return - int value that represents difference between days
     */
    public static int getDayDifference(Date startDate, Date endDate) {
        long timeDifference = endDate.getTime() - startDate.getTime();
        return (int) (((timeDifference / 1000) / 3600) / 24);
    }

    /**
     * @return java.util.Date with today value without milliseconds
     */
    public static Date getTodayWithoutMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis());
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * Adds one day to the given date
     *
     * @param date - given date
     * @return - given date + 1 day
     */
    public static Date addOneDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return new Date(calendar.getTimeInMillis());
    }
}
