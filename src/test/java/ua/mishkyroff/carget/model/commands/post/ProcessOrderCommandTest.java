package ua.mishkyroff.carget.model.commands.post;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestWrapper;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.dao.CarsDAO;
import ua.mishkyroff.carget.dao.OrdersDAO;
import ua.mishkyroff.carget.dao.UsersDAO;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.entities.FuelType;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.entities.User;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.RentPeriod;
import ua.mishkyroff.carget.model.commands.Command;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;
import static ua.mishkyroff.carget.model.Messages.ORDER_ADDED_SUCCESSFULLY;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
@RunWith(Theories.class)
public class ProcessOrderCommandTest extends Mockito {

    private static IRequestWrapper wrapper;
    private static AbstractDAOFactory daoFactory;
    private static CarsDAO carsDAO;
    private static OrdersDAO ordersDAO;
    private static UsersDAO usersDAO;
    private static Command command;
    private static Car car;
    private static User user;
    private static RentPeriod period;

    @Before
    public void setUp() throws Exception {
        command = new ProcessOrderCommand();
        period = spy(new RentPeriod());
        wrapper = mock(RequestWrapper.class);
        daoFactory = mock(AbstractDAOFactory.class);
        usersDAO = mock(UsersDAO.class);
        ordersDAO = mock(OrdersDAO.class);
        carsDAO = mock(CarsDAO.class);
        car = getDefaultCar();
        when(wrapper.getParameter("car_id")).thenReturn("1");
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getCarsDAO()).thenReturn(carsDAO);
        when(daoFactory.getUsersDAO()).thenReturn(usersDAO);
        when(usersDAO.getUserById(anyInt())).thenReturn(user);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
        when(carsDAO.getCarById(anyInt())).thenReturn(car);
    }

    private Car getDefaultCar() {
        return new Car(null, 2000, FuelType.ALL, new BigDecimal(10));
    }

    @After
    public void tearDown() throws Exception {
        command = null;
        wrapper = null;
        daoFactory = null;
        carsDAO = null;
        ordersDAO = null;
        usersDAO = null;
        period = null;
    }

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

    @Theory
    public void carExistsAndAvailableDatesCorrect(@FromDataPoints("startDates") LocalDate startDate,
                                                  @FromDataPoints("endDates") LocalDate endDate)
            throws Exception {
        assumeTrue(startDate.compareTo(LocalDate.now()) >= 0);
        assumeTrue(endDate.compareTo(startDate) > 0);
        String startDateString = startDate.toString();
        String endDateString = endDate.toString();
        when(wrapper.getParameter("start_date")).thenReturn(startDateString);
        when(wrapper.getParameter("end_date")).thenReturn(endDateString);
        when(carsDAO.checkAvailability(anyInt(), any(LocalDate.class), any(LocalDate.class))).thenReturn(true);
        when(ordersDAO.addOrder(any(Order.class))).thenReturn(true);

        View page = command.execute(wrapper);

        verify(wrapper, times(0)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_AUTO);
        verify(wrapper, times(0)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_CAR_IS_NOT_AVAILABLE_FOR_THIS_DATES);
        assertEquals(View.USER_MY_ORDERS, page);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ORDER_ADDED_SUCCESSFULLY);

    }

    @Theory
    public void orderAdditionError(@FromDataPoints("startDates") LocalDate startDate,
                                   @FromDataPoints("endDates") LocalDate endDate)
            throws Exception {
        assumeTrue(startDate.compareTo(LocalDate.now()) >= 0);
        assumeTrue(endDate.compareTo(startDate) > 0);
        String startDateString = startDate.toString();
        String endDateString = endDate.toString();
        when(wrapper.getParameter("start_date")).thenReturn(startDateString);
        when(wrapper.getParameter("end_date")).thenReturn(endDateString);
        when(carsDAO.checkAvailability(anyInt(), any(LocalDate.class), any(LocalDate.class))).thenReturn(true);
        when(ordersDAO.addOrder(any(Order.class))).thenReturn(false);

        View page = command.execute(wrapper);

        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_ORDER_NOT_ADDED_SUCCESSFULLY);
        assertEquals(View.USER_MY_ORDERS, page);

    }

    @Theory
    public void carExistsAndNotAvailableDatesCorrect(@FromDataPoints("startDates") LocalDate startDate,
                                                     @FromDataPoints("endDates") LocalDate endDate)
            throws Exception {
        assumeTrue(startDate.compareTo(LocalDate.now()) >= 0);
        assumeTrue(endDate.compareTo(startDate) > 0);
        String startDateString = startDate.toString();
        String endDateString = endDate.toString();
        when(wrapper.getParameter("start_date")).thenReturn(startDateString);
        when(wrapper.getParameter("end_date")).thenReturn(endDateString);
        when(carsDAO.checkAvailability(anyInt(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(false);

        View page = command.execute(wrapper);

        verify(wrapper, times(1)).setSessionAttribute(
                SessionAttributes.MESSAGE, Messages.ERROR_CAR_IS_NOT_AVAILABLE_FOR_THIS_DATES);
        assertEquals(View.CHOOSE_AUTO, page);
    }

    @Theory
    public void carDoesNotExistDatesCorrect(@FromDataPoints("startDates") LocalDate startDate,
                                            @FromDataPoints("endDates") LocalDate endDate)
            throws Exception {
        when(carsDAO.getCarById(anyInt())).thenReturn(null);
        assumeTrue(startDate.compareTo(LocalDate.now()) >= 0);
        assumeTrue(endDate.compareTo(startDate) > 0);
        String startDateString = startDate.toString();
        String endDateString = endDate.toString();
        when(wrapper.getParameter("start_date")).thenReturn(startDateString);
        when(wrapper.getParameter("end_date")).thenReturn(endDateString);

        View page = command.execute(wrapper);

        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_AUTO);
        verify(wrapper, times(0)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_CAR_IS_NOT_AVAILABLE_FOR_THIS_DATES);
        assertEquals(View.CHOOSE_AUTO, page);

    }

    @Test
    public void missingParams() {
        when(wrapper.getParameter("start_date")).thenReturn(null);
        when(wrapper.getParameter("end_date")).thenReturn(null);
        View page = command.execute(wrapper);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_AUTO);
        verify(wrapper, times(0)).setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_CAR_IS_NOT_AVAILABLE_FOR_THIS_DATES);
        assertEquals(View.CHOOSE_AUTO, page);
    }

    @Theory
    public void rentPeriodError(@FromDataPoints("startDates") LocalDate startDate,
                                @FromDataPoints("endDates") LocalDate endDate) {
        assumeTrue(startDate.compareTo(LocalDate.now()) < 0);
        assumeTrue(endDate.compareTo(startDate) <= 0);
        String startDateString = startDate.toString();
        String endDateString = endDate.toString();
        when(wrapper.getParameter("start_date")).thenReturn(startDateString);
        when(wrapper.getParameter("end_date")).thenReturn(endDateString);

        View page = command.execute(wrapper);

        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, "START_DATE_MUST_BE_GE_TODAY");
        assertEquals(View.CHOOSE_AUTO, page);
    }

}