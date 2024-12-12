package com.example.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car {

    private IntegerProperty carId;
    private StringProperty make;
    private StringProperty model;
    private IntegerProperty year;
    private DoubleProperty price;
    private IntegerProperty stock;
    private StringProperty vin;

    public Car(int carId, String make, String model, int year, double price, int stock, String vin) {
        this.carId = new SimpleIntegerProperty(carId);
        this.make = new SimpleStringProperty(make);
        this.model = new SimpleStringProperty(model);
        this.year = new SimpleIntegerProperty(year);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
        this.vin = new SimpleStringProperty(vin);
    }

    // Getters and Setters using JavaFX properties
    public int getCarId() {
        return carId.get();
    }

    public void setCarId(int carId) {
        this.carId.set(carId);
    }

    public IntegerProperty carIdProperty() {
        return carId;
    }

    public String getMake() {
        return make.get();
    }

    public void setMake(String make) {
        this.make.set(make);
    }

    public StringProperty makeProperty() {
        return make;
    }

    public String getModel() {
        return model.get();
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public StringProperty modelProperty() {
        return model;
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getStock() {
        return stock.get();
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public IntegerProperty stockProperty() {
        return stock;
    }

    public String getVin() {
        return vin.get();
    }

    public void setVin(String vin) {
        this.vin.set(vin);
    }

    public StringProperty vinProperty() {
        return vin;
    }
}
