package com.example.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Service {

    private IntegerProperty serviceId;
    private IntegerProperty carId;
    private IntegerProperty customerId;
    private StringProperty serviceDate;
    private StringProperty serviceDescription;
    private DoubleProperty cost;

    public Service(int serviceId, int carId, int customerId, String serviceDate, String serviceDescription, double cost) {
        this.serviceId = new SimpleIntegerProperty(serviceId);
        this.carId = new SimpleIntegerProperty(carId);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.serviceDate = new SimpleStringProperty(serviceDate);
        this.serviceDescription = new SimpleStringProperty(serviceDescription);
        this.cost = new SimpleDoubleProperty(cost);
    }

    public int getServiceId() {
        return serviceId.get();
    }

    public void setServiceId(int serviceId) {
        this.serviceId.set(serviceId);
    }

    public IntegerProperty serviceIdProperty() {
        return serviceId;
    }

    public int getCarId() {
        return carId.get();
    }

    public void setCarId(int carId) {
        this.carId.set(carId);
    }

    public IntegerProperty carIdProperty() {
        return carId;
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public String getServiceDate() {
        return serviceDate.get();
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate.set(serviceDate);
    }

    public StringProperty serviceDateProperty() {
        return serviceDate;
    }

    public String getServiceDescription() {
        return serviceDescription.get();
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription.set(serviceDescription);
    }

    public StringProperty serviceDescriptionProperty() {
        return serviceDescription;
    }

    public double getCost() {
        return cost.get();
    }

    public void setCost(double cost) {
        this.cost.set(cost);
    }

    public DoubleProperty costProperty() {
        return cost;
    }
}
