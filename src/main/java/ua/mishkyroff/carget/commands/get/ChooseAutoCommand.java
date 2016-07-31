package ua.mishkyroff.carget.commands.get;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.SessionAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.dao.CarFilter;

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
    public String execute(IRequestWrapper wrapper) {
        //create and init filter object with default params
        CarFilter carFilter = (CarFilter) wrapper
                .getSessionAttribute(SessionAttributes.CAR_FILTER);
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
        if (carFilter.haveUnreadedError()) {
            wrapper.setSessionAttribute(SessionAttributes.MESSAGE, carFilter.getError());
        }

        //update filter and filtered cars objects
        wrapper.setSessionAttribute(SessionAttributes.CAR_FILTER, carFilter);
        List<Car> cars = DAOFactory.getInstance().getCarsDAO().filterAndGetCars(carFilter);
        wrapper.setSessionAttribute(SessionAttributes.CARS, cars);
        return CHOOSE_AUTO;
    }


}
