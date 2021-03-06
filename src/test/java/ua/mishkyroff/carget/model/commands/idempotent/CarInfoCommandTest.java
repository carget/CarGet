package ua.mishkyroff.carget.model.commands.idempotent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.RequestWrapper;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOCommand;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.model.commands.Command;

import static org.junit.Assert.assertEquals;

/**
 * Test class for CarInfoCommand.class
 *
 * @author Anton Mishkyroff
 */
public class CarInfoCommandTest extends Mockito {
    private static IRequestWrapper wrapper;
    private static DAOManager daoManager;
    private static Car car;
    private static Command command;

    @Before
    public void setUp() throws Exception {
        wrapper = mock(RequestWrapper.class);
        daoManager = mock(DAOManager.class);
        command = new CarInfoCommand();
        car = mock(Car.class);
    }

    @After
    public void tearDown() throws Exception {
        wrapper = null;
        daoManager = null;
        command = null;
    }

    @Test
    public void notNullCarId() throws Exception {
        String carIdString = "1";
        when(wrapper.getParameter("car_id")).thenReturn(carIdString);
        when(wrapper.getDAOManager()).thenReturn(daoManager);
        when(daoManager.openExecuteAndClose(any(DAOCommand.class))).thenReturn(car);

        View page = command.execute(wrapper);

        assertEquals(View.CAR_INFO, page);
        verify(wrapper, times(1)).setRequestAttribute(RequestAttributes.CAR, car);
    }

    @Test
    public void nullCarId() throws Exception {
        String carIdString = null;
        when(wrapper.getParameter("car_id")).thenReturn(carIdString);
        View page = command.execute(wrapper);

        assertEquals(View.INDEX, page);
        verify(wrapper, times(0)).setRequestAttribute(RequestAttributes.CAR, car);
    }
}