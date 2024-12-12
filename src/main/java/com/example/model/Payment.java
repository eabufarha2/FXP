package com.example.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Payment {

    private IntegerProperty paymentId;
    private IntegerProperty orderId;
    private StringProperty paymentDate;
    private StringProperty paymentMethod;
    private DoubleProperty amount;

    public Payment(int paymentId, int orderId, String paymentDate, String paymentMethod, double amount) {
        this.paymentId = new SimpleIntegerProperty(paymentId);
        this.orderId = new SimpleIntegerProperty(orderId);
        this.paymentDate = new SimpleStringProperty(paymentDate);
        this.paymentMethod = new SimpleStringProperty(paymentMethod);
        this.amount = new SimpleDoubleProperty(amount);
    }

    public int getPaymentId() {
        return paymentId.get();
    }

    public void setPaymentId(int paymentId) {
        this.paymentId.set(paymentId);
    }

    public IntegerProperty paymentIdProperty() {
        return paymentId;
    }

    public int getOrderId() {
        return orderId.get();
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public String getPaymentDate() {
        return paymentDate.get();
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate.set(paymentDate);
    }

    public StringProperty paymentDateProperty() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod.get();
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod.set(paymentMethod);
    }

    public StringProperty paymentMethodProperty() {
        return paymentMethod;
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public DoubleProperty amountProperty() {
        return amount;
    }
}
