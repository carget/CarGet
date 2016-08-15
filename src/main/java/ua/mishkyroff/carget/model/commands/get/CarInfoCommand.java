package ua.mishkyroff.carget.model.commands.get;

import ua.mishkyroff.carget.controller.IRequestWrapper;
import ua.mishkyroff.carget.controller.RequestAttributes;
import ua.mishkyroff.carget.controller.View;
import ua.mishkyroff.carget.dao.AbstractDAOFactory;
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
    @Override
    public View execute(IRequestWrapper wrapper) {
        String carId = wrapper.getParameter("car_id");
        if (carId != null) {
            AbstractDAOFactory daoFactory = wrapper.getDAOFactory();
            Car car = daoFactory.getCarsDAO().getCarById(Integer.parseInt(carId));
            wrapper.setRequestAttribute(RequestAttributes.CAR, car);
        } else {
            return View.INDEX;
        }
        return View.CAR_INFO;
    }
}
