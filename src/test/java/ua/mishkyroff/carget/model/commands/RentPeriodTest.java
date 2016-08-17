package ua.mishkyroff.carget.model.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.RentPeriod;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

/**
 * Test class for RentPeriod.class
 *
 * @author Anton Mishkyroff
 */
@RunWith(Theories.class)
public class RentPeriodTest extends Mockito {

    private static RentPeriod period;

    @DataPoints(value = "startDates")
    public static LocalDate[] startDates() {
        return new LocalDate[]{
                LocalDate.now().minusDays(1),
                LocalDate.now(),
                LocalDate.now().plusDays(1),
        };
    }

    @DataPoints(value = "endDates")
    public static LocalDate[] endDates() {
        return new LocalDate[]{
                LocalDate.now().minusDays(1),
                LocalDate.now(),
                LocalDate.now().plusDays(1),
        };
    }

    @Before
    public void setUp() throws Exception {
        period = new RentPeriod();
    }

    @After
    public void tearDown() throws Exception {
        period = null;
    }

    @Theory
    public void setStartDateInvalid(@FromDataPoints("startDates") LocalDate startDate) throws
            Exception {
        assumeTrue(startDate.compareTo(LocalDate.now()) < 0);
        period.setStartDate(startDate);
        assertEquals(Messages.ERROR_START_DATE_MUST_BE_GE_TODAY, period.getError());
    }

    @Theory
    public void setStartDateValid(@FromDataPoints("startDates") LocalDate startDate) throws
            Exception {
        assumeTrue(startDate.compareTo(LocalDate.now()) >= 0);
        period.setStartDate(startDate);
        assertEquals(null, period.getError());
    }

    @Theory
    public void setEndDateInvalid(@FromDataPoints("startDates") LocalDate startDate,
                                  @FromDataPoints("endDates") LocalDate endDate) throws Exception {
        assumeTrue(startDate.compareTo(LocalDate.now()) >= 0);
        period.setStartDate(startDate);
        assumeTrue(endDate.compareTo(startDate) <= 0);
        //reset error
        period.getError();
        period.setEndDate(startDate);
        assertEquals(Messages.ERROR_END_DATE_MUST_BE_GT_START, period.getError());
    }

    @Theory
    public void setEndDateValid(@FromDataPoints("startDates") LocalDate startDate,
                                @FromDataPoints("endDates") LocalDate endDate) throws Exception {
        assumeTrue(startDate.compareTo(LocalDate.now()) >= 0);
        assumeTrue(endDate.compareTo(startDate) > 0);
        period.setStartDate(startDate);
        //reset error
        period.getError();
        period.setEndDate(endDate);
        assertEquals(null, period.getError());
    }

    @Test
    public void getDiffDays() throws Exception {
        LocalDate today = LocalDate.now();
        period.setStartDate(today);
        for (int i = 1; i <= 100; i++) {
            period.setEndDate(today.plusDays(i));
            assertEquals(i, period.getDiffDays());
        }
    }

}