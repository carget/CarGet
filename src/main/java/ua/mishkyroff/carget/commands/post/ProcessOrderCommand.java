package ua.mishkyroff.carget.commands.post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.CarFilter;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.entities.Order;
import ua.mishkyroff.carget.entities.OrderStatus;
import ua.mishkyroff.carget.entities.User;
import ua.mishkyroff.carget.utils.DateUtils;

import java.sql.Date;

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
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, FIRSTLY_CHOOSE_AUTO);
            return JspPages.CHOOSE_AUTO;
        } else {
            //validate dates and create an order
            //use filter as validator for dates
            CarFilter carFilter = new CarFilter();
            carFilter.setSelectedStartDate(startDateString);
            carFilter.setSelectedEndDate(endDateString);
            if (carFilter.haveUnreadedError()) {
                wrapper.setSessionAttribute(SessionAttributes.MESSAGE, carFilter.getError());
                return JspPages.CHOOSE_AUTO;
            }
            Date startDate = carFilter.getSelectedStartDate();
            Date endDate = carFilter.getSelectedEndDate();
            Car car = DAOFactory.getInstance().getCarsDAO().getCarById(Integer.parseInt(carId));
            //check availability for selected car and dates
            Boolean available = DAOFactory.getInstance().getCarsDAO()
                    .checkAvailability(car.getIdCar(), startDate, endDate);
            if (!available) {
                wrapper.setSessionAttribute(SessionAttributes.MESSAGE, ERROR_CAR_IS_NOT_AVAILABLE_FOR_THIS_DATES);
                return JspPages.CHOOSE_AUTO;
            }
            //calculate price
            Integer daysDiff = DateUtils.getDayDifference(startDate, endDate);
            double totalPrice = car.getPricePerDay() * daysDiff;
            Order order = new Order();
            User user = DAOFactory.getInstance().getUsersDAO()
                    .getUserById((Integer) wrapper.getSessionAttribute(SessionAttributes.USER_ID));
            order.setUser(user);
            order.setCar(car);
            order.setStartDate(startDate);
            order.setEndDate(endDate);
            order.setComment("");
            order.setRent(totalPrice);
            order.setFine(0);
            order.setStatus(OrderStatus.NEW);
            if (DAOFactory.getInstance().getOrdersDAO().addOrder(order)) {
                wrapper.setSessionAttribute(SessionAttributes.MESSAGE, ORDER_ADDED_SUCCESSFULLY);
                LOGGER.debug("new order added");
            }
        }
        return JspPages.USER_MY_ORDERS;
    }


}
