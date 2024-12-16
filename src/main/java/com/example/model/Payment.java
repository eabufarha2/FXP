package com.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private final IntegerProperty paymentID = new SimpleIntegerProperty(this, "paymentID");
    private final IntegerProperty orderID = new SimpleIntegerProperty(this, "orderID");
    private final ObjectProperty<LocalDate> paymentDate = new SimpleObjectProperty<>(this, "paymentDate");
    private final StringProperty paymentMethod = new SimpleStringProperty(this, "paymentMethod");
    private final ObjectProperty<BigDecimal> amount = new SimpleObjectProperty<>(this, "amount");

    public Payment() {
    }

    public Payment(int paymentID, int orderID, LocalDate paymentDate, String paymentMethod, BigDecimal amount) {
        setPaymentID(paymentID);
        setOrderID(orderID);
        setPaymentDate(paymentDate);
        setPaymentMethod(paymentMethod);
        setAmount(amount);
    }

    public int getPaymentID() {
        return paymentID.get();
    }

    public void setPaymentID(int value) {
        paymentID.set(value);
    }

    public IntegerProperty paymentIDProperty() {
        return paymentID;
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

    public LocalDate getPaymentDate() {
        return paymentDate.get();
    }

    public void setPaymentDate(LocalDate value) {
        paymentDate.set(value);
    }

    public ObjectProperty<LocalDate> paymentDateProperty() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod.get();
    }

    public void setPaymentMethod(String value) {
        paymentMethod.set(value);
    }

    public StringProperty paymentMethodProperty() {
        return paymentMethod;
    }

    public BigDecimal getAmount() {
        return amount.get();
    }

    public void setAmount(BigDecimal value) {
        amount.set(value);
    }

    public ObjectProperty<BigDecimal> amountProperty() {
        return amount;
    }
}
