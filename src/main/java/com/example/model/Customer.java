package com.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
    private final IntegerProperty customerID = new SimpleIntegerProperty(this, "customerID");
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    private final StringProperty email = new SimpleStringProperty(this, "email");
    private final StringProperty phone = new SimpleStringProperty(this, "phone");
    private final StringProperty address = new SimpleStringProperty(this, "address");
    private final StringProperty city = new SimpleStringProperty(this, "city");
    private final StringProperty state = new SimpleStringProperty(this, "state");
    private final StringProperty zipCode = new SimpleStringProperty(this, "zipCode");

    public Customer() {
    }

    public Customer(int customerID, String firstName, String lastName, String email, String phone, String address,
            String city, String state, String zipCode) {
        setCustomerID(customerID);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhone(phone);
        setAddress(address);
        setCity(city);
        setState(state);
        setZipCode(zipCode);
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

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String value) {
        firstName.set(value);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String value) {
        lastName.set(value);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String value) {
        phone.set(value);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String value) {
        address.set(value);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String value) {
        city.set(value);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getState() {
        return state.get();
    }

    public void setState(String value) {
        state.set(value);
    }

    public StringProperty stateProperty() {
        return state;
    }

    public String getZipCode() {
        return zipCode.get();
    }

    public void setZipCode(String value) {
        zipCode.set(value);
    }

    public StringProperty zipCodeProperty() {
        return zipCode;
    }
}