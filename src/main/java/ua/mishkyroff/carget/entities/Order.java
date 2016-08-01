package ua.mishkyroff.carget.entities;

import java.sql.Date;

/**
 * A Order entity stores information about order
 * The entity has its representation in the database
 *
 * @author Anton Mishkyroff
 */
public class Order {
    private int idOrder;
    private User user;
    private Car car;
    private Date startDate;
    private Date endDate;
    private String comment;
    private double rent;
    private double fine;
    private OrderStatus status;

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

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
