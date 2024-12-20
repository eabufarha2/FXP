package com.example.model;

import javafx.beans.property.*;

public class Car {
    private final IntegerProperty carID = new SimpleIntegerProperty(this, "carID");
    private final StringProperty make = new SimpleStringProperty(this, "make");
    private final StringProperty model = new SimpleStringProperty(this, "model");
    private final IntegerProperty year = new SimpleIntegerProperty(this, "year");
    private final DoubleProperty price = new SimpleDoubleProperty(this, "price");
    private final IntegerProperty stock = new SimpleIntegerProperty(this, "stock", 0);
    private final StringProperty vin = new SimpleStringProperty(this, "vin");

    public Car() {
    }

    public Car(int carID, String make, String model, int year, double price, int stock, String vin) {
        setCarID(carID);
        setMake(make);
        setModel(model);
        setYear(year);
        setPrice(price);
        setStock(stock);
        setVin(vin);
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

    public String getMake() {
        return make.get();
    }

    public void setMake(String value) {
        make.set(value);
    }

    public StringProperty makeProperty() {
        return make;
    }

    public String getModel() {
        return model.get();
    }

    public void setModel(String value) {
        model.set(value);
    }

    public StringProperty modelProperty() {
        return model;
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int value) {
        year.set(value);
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double value) {
        price.set(value);
    }

    public int getStock() {
        return stock.get();
    }

    public void setStock(int value) {
        stock.set(value);
    }

    public IntegerProperty stockProperty() {
        return stock;
    }

    public String getVin() {
        return vin.get();
    }

    public void setVin(String value) {
        vin.set(value);
    }

    public StringProperty vinProperty() {
        return vin;
    }
}
