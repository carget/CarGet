package ua.mishkyroff.carget.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Order entity stores information about order
 * The entity has its representation in the database
 *
 * @author Anton Mishkyroff
 */
public class Order {
    private int idOrder;
    private final User user;
    private final Car car;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private String comment;
    private final BigDecimal rent;
    private BigDecimal fine;
    private OrderStatus status;

    public Order(User user, Car car, LocalDate startDate, LocalDate endDate, String comment, BigDecimal rent, BigDecimal fine, OrderStatus status) {
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comment = comment;
        this.rent = rent;
        this.fine = fine;
        this.status = status;
    }

    public Order(int idOrder, User user, Car car, LocalDate startDate, LocalDate endDate, String comment, BigDecimal rent, BigDecimal fine, OrderStatus status) {
        this(user, car, startDate, endDate, comment, rent, fine, status);
        this.idOrder = idOrder;
    }

    @Override
    public String toString() {
        return "ORDER id=" + idOrder + " USER " + user + " CAR " + car + " StartDate=" +
                startDate + " EndDate=" + endDate + " Comment=" + comment + " Rent=" + rent + " " +
                "fine=" + fine + " Status=" + status;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
