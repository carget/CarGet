package ua.mishkyroff.carget.controller;

/**
 *  This enum stores view URI and corresponding path to .jsp file
 *
 *  @author Anton Mishkyroff
 */
public enum View {
    INDEX("/WEB-INF/jsp/index.jsp"),
    GUEST_REGISTER("/WEB-INF/jsp/register.jsp"),
    CHANGE_LANG("/WEB-INF/jsp/change_lang.jsp"),
    LOGOUT("/WEB-INF/jsp/logout.jsp"),
    CHOOSE_AUTO("/WEB-INF/jsp/choose_auto.jsp"),
    USER_PREPARE_ORDER("/WEB-INF/jsp/prepare_order.jsp"),
    USER_MY_ORDERS("/WEB-INF/jsp/my_orders.jsp"),
    CAR_INFO("/WEB-INF/jsp/car_info.jsp"),
    LOGIN("/WEB-INF/jsp/login.jsp"),
    REGISTRATION("/WEB-INF/jsp/registration.jsp"),
    PROCESS_ORDER("/WEB-INF/jsp/process_order.jsp"),
    ERROR_404("/error_404.jsp"),
    ERROR_ROLE("/error_role.jsp"),
    ADMIN_NEW_ORDERS("/WEB-INF/jsp/admin/new_orders.jsp"),
    ADMIN_APPROVED_ORDERS("/WEB-INF/jsp/admin/approved_orders.jsp"),
    ADMIN_COMPLETED_ORDERS("/WEB-INF/jsp/admin/completed_orders.jsp"),
    ADMIN_PAID_ORDERS("/WEB-INF/jsp/admin/paid_orders.jsp"),
    ADMIN_REJECTED_ORDERS("/WEB-INF/jsp/admin/rejected_orders.jsp"),
    ADMIN_CANCELED_ORDERS("/WEB-INF/jsp/admin/canceled_orders.jsp"),
    ADMIN_RETURN_CAR("/WEB-INF/jsp/admin/return_car.jsp");

    private String path;

    View(String path) {
        this.path = path;
    }

    /**
     * @return - relative path of .jsp file
     */
    public String getJspPath() {
        return path;
    }


    public String getURI() {
        return super.toString().toLowerCase();
    }
}
