package ua.mishkyroff.carget.commands.get;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.commands.CarFilter;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestAttributes;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.Car;

import java.util.List;

/**
 * A command for process GET requests with "/choose_auto" uri
 * The command uses CarFilter object to filter all cars
 * The command can set message attribute in session if
 * some filters errors are present
 * The command forwards to corresponding .jsp file
 *
 * @author Anton Mishkyroff
 * @see CarFilter
 */
public class ChooseAutoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public JspPages execute(IRequestWrapper wrapper) {
        //create and init filter object with default params
        CarFilter carFilter = (CarFilter) wrapper
                .getRequestAttribute(RequestAttributes.CAR_FILTER);
        if (carFilter == null) {
            carFilter = new CarFilter();
        }
        //set filter's params from request to filter object
        carFilter.setSelectedBrandId(wrapper.getParameter("brand_id"));
        carFilter.setSelectedYear(wrapper.getParameter("year"));
        carFilter.setSelectedFuelType(wrapper.getParameter("fuel_type"));
        carFilter.setSelectedAutomat(wrapper.getParameter("automat"));
        carFilter.setSelectedCondition(wrapper.getParameter("condition"));
        carFilter.setSelectedStartDate(wrapper.getParameter("start_date"));
        carFilter.setSelectedEndDate(wrapper.getParameter("end_date"));
        carFilter.setSelectedLowPrice(wrapper.getParameter("low_price"));
        carFilter.setSelectedHiPrice(wrapper.getParameter("hi_price"));
        if (carFilter.haveUnreadError()) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, carFilter.getError());
        }

        //update filter and filtered cars objects
        wrapper.setRequestAttribute(RequestAttributes.CAR_FILTER, carFilter);
        DAOFactory daoFactory = wrapper.getDAOFactory();
        List<Car> cars = daoFactory.getCarsDAO().filterAndGetCars(carFilter);
        wrapper.setRequestAttribute(RequestAttributes.CARS, cars);
        return JspPages.CHOOSE_AUTO;
    }


}
