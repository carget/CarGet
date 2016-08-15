package ua.mishkyroff.carget.model.commands.get;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.RequestWrapper;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.dao.CarsDAO;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.model.commands.Command;

import static org.junit.Assert.assertEquals;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
public class CarInfoCommandTest extends Mockito{
    private static IRequestWrapper wrapper;
    private static AbstractDAOFactory daoFactory;
    private static CarsDAO carsDAO;
    private static Car car;
    private static Command command;

    @Before
    public void setUp() throws Exception {
        wrapper = mock(RequestWrapper.class);
        daoFactory = mock(AbstractDAOFactory.class);
        carsDAO = mock(CarsDAO.class);
        command = new CarInfoCommand();
        car = mock(Car.class);
    }

    @After
    public void tearDown() throws Exception {
        wrapper = null;
        daoFactory = null;
        carsDAO = null;
        command = null;
    }

    @Test
    public void notNullCarId() throws Exception {
        String carIdString = "1";
        when(wrapper.getParameter("car_id")).thenReturn(carIdString);
        when(wrapper.getDAOFactory()).thenReturn(daoFactory);
        when(daoFactory.getCarsDAO()).thenReturn(carsDAO);
        when(carsDAO.getCarById(Integer.parseInt(carIdString))).thenReturn(car);

        View page = command.execute(wrapper);

        assertEquals(page, View.CAR_INFO);
        verify(wrapper, times(1)).setRequestAttribute(RequestAttributes.CAR, car);
    }

    @Test
    public void nullCarId() throws Exception {
        String carIdString = null;
        when(wrapper.getParameter("car_id")).thenReturn(carIdString);
        View page = command.execute(wrapper);

        assertEquals(page, View.INDEX);
        verify(wrapper, times(0)).setRequestAttribute(RequestAttributes.CAR, car);
    }
}