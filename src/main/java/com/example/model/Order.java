package com.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {
    private final IntegerProperty orderID = new SimpleIntegerProperty(this, "orderID");
    private final ObjectProperty<LocalDate> orderDate = new SimpleObjectProperty<>(this, "orderDate");
    private final IntegerProperty carID = new SimpleIntegerProperty(this, "carID");
    private final IntegerProperty customerID = new SimpleIntegerProperty(this, "customerID");
    private final IntegerProperty employeeID = new SimpleIntegerProperty(this, "employeeID");
    private final IntegerProperty quantity = new SimpleIntegerProperty(this, "quantity", 1);
    private final ObjectProperty<BigDecimal> totalPrice = new SimpleObjectProperty<>(this, "totalPrice");

    public Order() {
    }

    public Order(int orderID, LocalDate orderDate, int carID, int customerID, Integer employeeID, int quantity,
            BigDecimal totalPrice) {
        setOrderID(orderID);
        setOrderDate(orderDate);
        setCarID(carID);
        setCustomerID(customerID);
        if (employeeID != null)
            setEmployeeID(employeeID);
        setQuantity(quantity);
        setTotalPrice(totalPrice);
    }

    public int getOrderID() {
        return orderID.get();
    }

    public void setOrderID(int value) {
        orderID.set(value);
    }

    public IntegerProperty orderIDProperty() {
        return orderID;
    }

    public LocalDate getOrderDate() {
        return orderDate.get();
    }

    public void setOrderDate(LocalDate value) {
        orderDate.set(value);
    }

    public ObjectProperty<LocalDate> orderDateProperty() {
        return orderDate;
    }

    public int getCarID() {
        return carID.get();
    }

    public void setCarID(int value) {
        carID.set(value);
    }

    public IntegerProperty carIDProperty() {
        return carID;
    }

    public int getCustomerID() {
        return customerID.get();
    }

    public void setCustomerID(int value) {
        customerID.set(value);
    }

    public IntegerProperty customerIDProperty() {
        return customerID;
    }

    public Integer getEmployeeID() {
        return employeeID.get() == 0 ? null : employeeID.get();
    }

    public void setEmployeeID(int value) {
        employeeID.set(value);
    }

    public IntegerProperty employeeIDProperty() {
        return employeeID;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int value) {
        quantity.set(value);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice.get();
    }

    public void setTotalPrice(BigDecimal value) {
        totalPrice.set(value);
    }

    public ObjectProperty<BigDecimal> totalPriceProperty() {
        return totalPrice;
    }
}
