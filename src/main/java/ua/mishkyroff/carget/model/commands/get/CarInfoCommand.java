package ua.mishkyroff.carget.model.commands.get;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.DAOManager;
import ua.mishkyroff.carget.dao.Exceptions.DBException;
import ua.mishkyroff.carget.entities.Car;
import ua.mishkyroff.carget.model.commands.Command;

/**
 * A command for process GET requests with "/car_info" uri
 * The command gets information about car with corresponding car_id
 * and forwards for corresponding .jsp file
 * If car_id is not specified then forwards to main page (index)
 *
 * @author Anton Mishkyroff
 */
public class CarInfoCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger("toConsole");

    @Override
    public View execute(IRequestWrapper wrapper) {
        String carId = wrapper.getParameter("car_id");
        if (carId == null) {
            return View.INDEX;
        }
        DAOManager daoManager = wrapper.getDAOManager();
        Car car = null;
        try {
            daoManager.openConnection();
            car = daoManager.getCarsDAO().getCarById(Integer.parseInt(carId));
        } catch (DBException e) {
            LOGGER.error(e);
        } finally {
            daoManager.closeConnection();
        }
        if (car == null) {
            return View.INDEX;
        }
        wrapper.setRequestAttribute(RequestAttributes.CAR, car);
        return View.CAR_INFO;
    }
}

