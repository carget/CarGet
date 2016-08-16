package ua.mishkyroff.carget.model.commands.get;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.*;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.dao.CarsDAO;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.model.CarFilter;
import ua.mishkyroff.carget.model.commands.Command;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public class ChooseAutoCommandTest extends Mockito {
    private static IRequestWrapper wrapper;
    private static AbstractDAOFactory daoFactory;
    private static CarsDAO carsDAO;
    private static Command command;
    private static List<Car> carList;
    private static CarFilter carFilter;

    @Before
    public void setUp() throws Exception {
        wrapper = mock(RequestWrapper.class);
        daoFactory = mock(AbstractDAOFactory.class);
        carsDAO = mock(CarsDAO.class);
        command = new ChooseAutoCommand();
        carFilter = mock(CarFilter.class);
    }

    @After
    public void tearDown() throws Exception {
        wrapper = null;
        daoFactory = null;
        carList = null;
        carFilter = null;
        carsDAO = null;
    }

    @Test
    public void execute() throws Exception {
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getCarsDAO()).thenReturn(carsDAO);
        when(wrapper.getRequestAttribute(RequestAttributes.CAR_FILTER)).thenReturn(carFilter);
        when(wrapper.getParameter("brand_id")).thenReturn("-1");
        when(wrapper.getParameter("year")).thenReturn("-1");
        when(wrapper.getParameter("fuel_type")).thenReturn("-1");
        when(wrapper.getParameter("automat")).thenReturn("-1");
        when(wrapper.getParameter("condition")).thenReturn("-1");
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        when(wrapper.getParameter("start_date")).thenReturn(today.toString());
        when(wrapper.getParameter("end_date")).thenReturn(tomorrow.toString());
        when(carsDAO.filterAndGetCars(carFilter)).thenReturn(carList);

        View page = command.execute(wrapper);

        assertEquals(page, View.CHOOSE_AUTO);
        verify(wrapper, times(0)).setSessionAttribute(any(SessionAttributes.class), anyString());
        verify(wrapper, times(1)).setRequestAttribute(RequestAttributes.CAR_FILTER, carFilter);
        verify(wrapper, times(1)).setRequestAttribute(RequestAttributes.CARS, carList);

    }

}