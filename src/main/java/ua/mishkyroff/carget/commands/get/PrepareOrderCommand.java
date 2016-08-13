package ua.mishkyroff.carget.commands.get;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.commands.Messages;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestAttributes;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.Car;

import java.time.LocalDate;

/**
 * A command for process GET requests with "/user_prepare_order" uri
 * The command validates request's parameters and saves error messages to
 * session if there are some problems with them
 * The command forwards to order's confirmation page
 *
 * @author Anton Mishkyroff
 */
public class PrepareOrderCommand implements Command {
    @Override
    public JspPages execute(IRequestWrapper wrapper) {

        String startDate = wrapper.getParameter("start_date");
        String endDate = wrapper.getParameter("end_date");
        String carId = wrapper.getParameter("car_id");

        if (startDate == null || endDate == null || carId == null) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_CHOOSING_CAR);
            return JspPages.CHOOSE_AUTO;
        }
        DAOFactory daoFactory = wrapper.getDAOFactory();
        Car car = daoFactory.getCarsDAO().getCarById(Integer.valueOf(carId));
        if (car == null) {
            //set error message and goto choose auto
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, Messages.ERROR_CHOOSING_CAR);
            return JspPages.CHOOSE_AUTO;
        } else {
            //save car and dates into request to show on confirmation page
            wrapper.setRequestAttribute(RequestAttributes.CAR, car);
            wrapper.setRequestAttribute(RequestAttributes.START_DATE, LocalDate.parse(startDate));
            wrapper.setRequestAttribute(RequestAttributes.END_DATE, LocalDate.parse(endDate));
        }
        return JspPages.USER_PREPARE_ORDER;
    }
}
