package ua.mishkyroff.carget.commands.post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.commands.CarFilter;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.commands.Messages;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.entities.OrderStatus;
import ua.mishkyroff.carget.entities.User;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * A command for process POST requests with "/user_process_order" uri
 * The command validates given input parameters
 * The command sets error message if some parameters are missing or user invalid
 * If user valid fills session attributes (user_name, user_id, user_role)
 *
 * @author Anton Mishkyroff
 */
public class ProcessOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public JspPages execute(IRequestWrapper wrapper) {
        String carId = wrapper.getParameter("car_id");
        String startDateString = wrapper.getParameter("start_date");
        String endDateString = wrapper.getParameter("end_date");

        if (carId == null || startDateString == null || endDateString == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_AUTO);
            return JspPages.CHOOSE_AUTO;
        }
        //validate dates and create an order
        //use filter as validator for dates
        CarFilter carFilter = new CarFilter();
        carFilter.setSelectedStartDate(startDateString);
        carFilter.setSelectedEndDate(endDateString);
        if (carFilter.haveUnreadError()) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, carFilter.getError());
            return JspPages.CHOOSE_AUTO;
        }
        LocalDate startDate = carFilter.getSelectedStartDate();
        LocalDate endDate = carFilter.getSelectedEndDate();
        DAOFactory daoFactory = wrapper.getDAOFactory();
        Car car = daoFactory.getCarsDAO().getCarById(Integer.parseInt(carId));
        if (car == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_AUTO);
            return JspPages.CHOOSE_AUTO;
        }
        //check availability for selected car and dates
        Boolean available = daoFactory.getCarsDAO().checkAvailability(car.getIdCar(), startDate, endDate);
        if (!available) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_CAR_IS_NOT_AVAILABLE_FOR_THIS_DATES);
            return JspPages.CHOOSE_AUTO;
        }
        //calculate price
//        Duration daysDiff = Duration.between(startDate, endDate);
//        Integer daysDiff = DateUtils.getDayDifference(startDate, endDate);
        double totalPrice = car.getPricePerDay() * DAYS.between(startDate, endDate);
        User user = daoFactory.getUsersDAO().getUserById((Integer) wrapper.getSessionAttribute(SessionAttributes.USER_ID));
        Order order = new Order(user, car, startDate, endDate, "", totalPrice, 0, OrderStatus.NEW);
        if (daoFactory.getOrdersDAO().addOrder(order)) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ORDER_ADDED_SUCCESSFULLY);
            LOGGER.debug("new order added");
        } else {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_ORDER_NOT_ADDED_SUCCESSFULLY);
            LOGGER.debug("new order NOT added");
        }

        return JspPages.USER_MY_ORDERS;
    }


}
