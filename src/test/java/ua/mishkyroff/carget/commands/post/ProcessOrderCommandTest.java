package ua.mishkyroff.carget.commands.post;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import ua.mishkyroff.carget.commands.CarFilter;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestWrapper;
import ua.mishkyroff.carget.dao.CarsDAO;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.dao.OrdersDAO;
import ua.mishkyroff.carget.dao.UsersDAO;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.entities.User;

import java.time.LocalDate;

/**
 * //TODO add comments
 *
 * @author Anton Mishkyroff
 */
@RunWith(Theories.class)
@Ignore
public class ProcessOrderCommandTest extends Mockito {

    private static IRequestWrapper wrapper;
    private static DAOFactory daoFactory;
    private static CarsDAO carsDAO;
    private static OrdersDAO ordersDAO;
    private static UsersDAO usersDAO;
    private static Command command;
    private static Car car;
    private static User user;
    private static CarFilter filter;

    @Before
    public void setUp() throws Exception {
        command = new ProcessOrderCommand();
        wrapper = mock(RequestWrapper.class);
        daoFactory = mock(DAOFactory.class);
        usersDAO = mock(UsersDAO.class);
        ordersDAO = mock(OrdersDAO.class);
        carsDAO = mock(CarsDAO.class);
        filter = mock(CarFilter.class);

        when(wrapper.getParameter("car_id")).thenReturn("1");
        when(daoFactory.getCarsDAO()).thenReturn(carsDAO);
        when(daoFactory.getUsersDAO()).thenReturn(usersDAO);
//        user = new User();
        when(usersDAO.getUserById(anyInt())).thenReturn(user);
        when(daoFactory.getOrdersDAO()).thenReturn(ordersDAO);
//        car = new Car();
        when(carsDAO.getCarById(anyInt())).thenReturn(car);

    }

    @After
    public void tearDown() throws Exception {
        command = null;
        wrapper = null;
        daoFactory = null;
        carsDAO = null;
        ordersDAO = null;
        usersDAO = null;
    }

    @DataPoints("dates")
    public static String[][] datesForTest() {
        return new String[][]{
                {"01.01.2016", "02.02.2016"},
                {"01.01.2016", "02.02.2016"},
                {"01.01.2016", "02.02.2016"}
        };
    }

    @Theory
    public void carExistsAndAvailableDatesCorrect(@FromDataPoints("dates") String[] dates) throws
            Exception {
        String startDate = dates[0];
        String endDate = dates[1];
        when(wrapper.getParameter("start_date")).thenReturn(startDate);
        when(wrapper.getParameter("end_date")).thenReturn(endDate);
        when(carsDAO.checkAvailability(anyInt(), any(LocalDate.class), any(LocalDate.class))).thenReturn
                (true);
        when(ordersDAO.addOrder(any(Order.class))).thenReturn(true);

         JspPages page = command.execute(wrapper);

        //  assertEquals(JspPages.USER_MY_ORDERS, page);
        //  verify(wrapper, times(1)).setSessionAttribute(SessionAttributes.MESSAGE, ORDER_ADDED_SUCCESSFULLY);

    }

}