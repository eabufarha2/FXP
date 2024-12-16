package com.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private final IntegerProperty employeeID = new SimpleIntegerProperty(this, "employeeID");
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    private final StringProperty position = new SimpleStringProperty(this, "position");
    private final ObjectProperty<BigDecimal> salary = new SimpleObjectProperty<>(this, "salary");
    private final ObjectProperty<LocalDate> hireDate = new SimpleObjectProperty<>(this, "hireDate");

    public Employee() {
    }

    public Employee(int employeeID, String firstName, String lastName, String position, BigDecimal salary,
            LocalDate hireDate) {
        setEmployeeID(employeeID);
        setFirstName(firstName);
        setLastName(lastName);
        setPosition(position);
        setSalary(salary);
        setHireDate(hireDate);
    }

    public int getEmployeeID() {
        return employeeID.get();
    }

    public void setEmployeeID(int value) {
        employeeID.set(value);
    }

    public IntegerProperty employeeIDProperty() {
        return employeeID;
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

    public String getPosition() {
        return position.get();
    }

    public void setPosition(String value) {
        position.set(value);
    }

    public StringProperty positionProperty() {
        return position;
    }

    public BigDecimal getSalary() {
        return salary.get();
    }

    public void setSalary(BigDecimal value) {
        salary.set(value);
    }

    public ObjectProperty<BigDecimal> salaryProperty() {
        return salary;
    }

    public LocalDate getHireDate() {
        return hireDate.get();
    }

    public void setHireDate(LocalDate value) {
        hireDate.set(value);
    }

    public ObjectProperty<LocalDate> hireDateProperty() {
        return hireDate;
    }
}
