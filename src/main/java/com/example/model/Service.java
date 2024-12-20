package com.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Service {
    private final IntegerProperty serviceID = new SimpleIntegerProperty(this, "serviceID");
    private final IntegerProperty carID = new SimpleIntegerProperty(this, "carID");
    private final IntegerProperty customerID = new SimpleIntegerProperty(this, "customerID");
    private final ObjectProperty<LocalDate> serviceDate = new SimpleObjectProperty<>(this, "serviceDate");
    private final StringProperty serviceDescription = new SimpleStringProperty(this, "serviceDescription");
    private final DoubleProperty cost = new SimpleDoubleProperty(this, "cost");

    public Service() {
    }

    public Service(int serviceID, int carID, int customerID, LocalDate serviceDate, String serviceDescription,
            Double cost) {
        setServiceID(serviceID);
        setCarID(carID);
        setCustomerID(customerID);
        setServiceDate(serviceDate);
        setServiceDescription(serviceDescription);
        setCost(cost);
    }

    public int getServiceID() {
        return serviceID.get();
    }

    public void setServiceID(int value) {
        serviceID.set(value);
    }

    public IntegerProperty serviceIDProperty() {
        return serviceID;
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

    public LocalDate getServiceDate() {
        return serviceDate.get();
    }

    public void setServiceDate(LocalDate value) {
        serviceDate.set(value);
    }

    public ObjectProperty<LocalDate> serviceDateProperty() {
        return serviceDate;
    }

    public String getServiceDescription() {
        return serviceDescription.get();
    }

    public void setServiceDescription(String value) {
        serviceDescription.set(value);
    }

    public StringProperty serviceDescriptionProperty() {
        return serviceDescription;
    }

    public double getCost() {
        return cost.get();
    }

    public void setCost(double value) {
        cost.set(value);
    }

    public DoubleProperty costProperty() {
        return cost;
    }
}
