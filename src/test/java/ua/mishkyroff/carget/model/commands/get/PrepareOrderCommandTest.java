package ua.mishkyroff.carget.model.commands.get;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.*;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.dao.CarsDAO;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.model.commands.Command;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static ua.mishkyroff.carget.model.Messages.ERROR_CHOOSING_CAR;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public class PrepareOrderCommandTest extends Mockito {

    private static Command command;
    private static AbstractDAOFactory daoFactory;
    private static CarsDAO carsDAO;
    private static IRequestWrapper wrapper;
    private static Car car;
    private static LocalDate startDate;
    private static LocalDate endDate;


    @Before
    public void setUp() throws Exception {
        wrapper = mock(RequestWrapper.class);
        command = new PrepareOrderCommand();
        daoFactory = mock(AbstractDAOFactory.class);
        carsDAO = mock(CarsDAO.class);
        car = mock(Car.class);
        startDate = LocalDate.now();
        endDate = startDate.plusDays(1);
    }

    @After
    public void tearDown() throws Exception {
        wrapper = null;
        command = null;
        daoFactory = null;
        carsDAO = null;
        car = null;
    }

    @Test
    public void paramsNotNull() throws Exception {

        String carId = "1";
        when(wrapper.getParameter("start_date")).thenReturn(startDate.toString());
        when(wrapper.getParameter("end_date")).thenReturn(endDate.toString());
        when(wrapper.getParameter("car_id")).thenReturn(carId);
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getCarsDAO()).thenReturn(carsDAO);
        when(carsDAO.getCarById(Integer.parseInt(carId))).thenReturn(car);

        View page = command.execute(wrapper);

        assertEquals(page, View.USER_PREPARE_ORDER);
        verify(wrapper, times(1)).setRequestAttribute(RequestAttributes.CAR, car);
        verify(wrapper, times(1)).setRequestAttribute(RequestAttributes.START_DATE, startDate);
        verify(wrapper, times(1)).setRequestAttribute(RequestAttributes.END_DATE, endDate);


    }

    @Test
    public void paramsNull() throws Exception {
        String carId = "1";
        when(wrapper.getParameter("start_date")).thenReturn(null);
        when(wrapper.getParameter("end_date")).thenReturn(endDate.toString());
        when(wrapper.getParameter("car_id")).thenReturn(carId);
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getCarsDAO()).thenReturn(carsDAO);
        when(carsDAO.getCarById(Integer.parseInt(carId))).thenReturn(car);

        View page = command.execute(wrapper);

        assertEquals(page, View.CHOOSE_AUTO);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ERROR_CHOOSING_CAR);
        verify(wrapper, times(0)).setRequestAttribute(RequestAttributes.CAR, car);
        verify(wrapper, times(0)).setRequestAttribute(RequestAttributes.START_DATE, startDate);
        verify(wrapper, times(0)).setRequestAttribute(RequestAttributes.END_DATE, endDate);
    }

    @Test
    public void carNull() throws Exception {
        String carId = "1";
        when(wrapper.getParameter("start_date")).thenReturn(startDate.toString());
        when(wrapper.getParameter("end_date")).thenReturn(endDate.toString());
        when(wrapper.getParameter("car_id")).thenReturn(carId);
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getCarsDAO()).thenReturn(carsDAO);
        when(carsDAO.getCarById(Integer.parseInt(carId))).thenReturn(null);

        View page = command.execute(wrapper);

        assertEquals(page, View.CHOOSE_AUTO);
        verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ERROR_CHOOSING_CAR);
        verify(wrapper, times(0)).setRequestAttribute(RequestAttributes.CAR, car);
        verify(wrapper, times(0)).setRequestAttribute(RequestAttributes.START_DATE, startDate);
        verify(wrapper, times(0)).setRequestAttribute(RequestAttributes.END_DATE, endDate);
    }
}