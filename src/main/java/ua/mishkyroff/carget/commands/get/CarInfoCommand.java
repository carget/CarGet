package ua.mishkyroff.carget.commands.get;

import ua.mishkyroff.carget.commands.Command;
import ua.mishkyroff.carget.controllers.IRequestWrapper;
import ua.mishkyroff.carget.controllers.JspPages;
import ua.mishkyroff.carget.controllers.RequestAttributes;
import ua.mishkyroff.carget.dao.DAOFactory;
import ua.mishkyroff.carget.entities.Car;

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
    public JspPages execute(IRequestWrapper wrapper) {
        String carId = wrapper.getParameter("car_id");
        if (carId != null) {
            DAOFactory daoFactory = wrapper.getDAOFactory();
            Car car = daoFactory.getCarsDAO().getCarById(Integer.parseInt(carId));
            wrapper.setRequestAttribute(RequestAttributes.CAR, car);
        } else {
            return JspPages.INDEX;
        }
        return JspPages.CAR_INFO;
    }
}
