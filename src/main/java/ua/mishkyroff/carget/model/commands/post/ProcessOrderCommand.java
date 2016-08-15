package ua.mishkyroff.carget.model.commands.post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.SessionAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
import ua.mishkyroff.carget.dao.CarsDAO;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.entities.OrderStatus;
import ua.mishkyroff.carget.entities.User;
import ua.mishkyroff.carget.model.Messages;
import ua.mishkyroff.carget.model.RentPeriod;
import ua.mishkyroff.carget.model.commands.Command;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    public View execute(IRequestWrapper wrapper) {
        String carId = wrapper.getParameter("car_id");
        String startDateString = wrapper.getParameter("start_date");
        String endDateString = wrapper.getParameter("end_date");

        if (carId == null || startDateString == null || endDateString == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_AUTO);
            return View.CHOOSE_AUTO;
        }
        //validate dates and create an order
        RentPeriod rentPeriod = new RentPeriod(LocalDate.parse(startDateString), LocalDate.parse(endDateString));
        if (rentPeriod.haveUnreadError()) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, rentPeriod.getError());
            return View.CHOOSE_AUTO;
        }
        LocalDate startDate = rentPeriod.getStartDate();
        LocalDate endDate = rentPeriod.getEndDate();
        AbstractDAOFactory daoFactory = wrapper.getDAOFactory();
        CarsDAO carsDAO = daoFactory.getCarsDAO();
        Car car = carsDAO.getCarById(Integer.parseInt(carId));
        if (car == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.FIRSTLY_CHOOSE_AUTO);
            return View.CHOOSE_AUTO;
        }
        //check availability for selected car and dates
        Boolean available = carsDAO.checkAvailability(car.getIdCar(), startDate, endDate);
        if (!available) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_CAR_IS_NOT_AVAILABLE_FOR_THIS_DATES);
            return View.CHOOSE_AUTO;
        }
        //calculate price
        BigDecimal totalPrice = rentPeriod.getTotalRent(car.getPricePerDay());
//        BigDecimal totalPrice = car.getPricePerDay().multiply(rentPeriod.getDiffDays());
        User user = daoFactory.getUsersDAO().getUserById((Integer) wrapper.getSessionAttribute(SessionAttributes.USER_ID));
        Order order = new Order(user, car, startDate, endDate, "", totalPrice, new BigDecimal(0), OrderStatus.NEW);
        if (daoFactory.getOrdersDAO().addOrder(order)) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ORDER_ADDED_SUCCESSFULLY);
            LOGGER.debug("new order added");
        } else {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_ORDER_NOT_ADDED_SUCCESSFULLY);
            LOGGER.debug("new order NOT added");
        }
        return View.USER_MY_ORDERS;
    }


}
