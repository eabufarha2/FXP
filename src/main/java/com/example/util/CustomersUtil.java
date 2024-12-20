package com.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

import com.example.components.CustomAlerts;
import com.example.components.CustomField;
import com.example.config.databaseConfig;
import com.example.model.Customer;
import com.example.util.FXUtils;

public class CustomersUtil {
    public ObservableList<Customer> customers;

    public CustomersUtil() {
        refresh();
    }

    public void refresh() {
        String query = "SELECT * FROM customers";
        customers = databaseConfig.fetchData(query, rs -> {
            try {
                return new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Address"),
                        rs.getString("City"),
                        rs.getString("State"),
                        rs.getString("ZipCode"));
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        if (customers == null) {
            customers = FXCollections.observableArrayList();
        }
    }

    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public static boolean insert(CustomField firstNameField, CustomField lastNameField,
            CustomField emailField, CustomField phoneField, CustomField addressField,
            CustomField cityField, CustomField stateField, CustomField zipCodeField) {

        boolean valid = true;

        // We only check null fields and basic validations now.
        if (!FXUtils.checkNullField(firstNameField))
            valid = false;

        if (!FXUtils.checkNullField(lastNameField))
            valid = false;

        if (!FXUtils.checkNullField(emailField))
            valid = false;
        if (!FXUtils.isValidEmail(emailField))
            valid = false;

        if (!FXUtils.checkNullField(phoneField))
            valid = false;
        if (!FXUtils.isValidPhone(phoneField))
            valid = false;

        if (!FXUtils.checkNullField(addressField))
            valid = false;
        if (!FXUtils.checkNullField(cityField))
            valid = false;
        if (!FXUtils.checkNullField(stateField))
            valid = false;

        if (!FXUtils.checkNullField(zipCodeField))
            valid = false;
        if (!FXUtils.checkNumericField(zipCodeField))
            valid = false;

        if (!valid) {
            return false;
        }

        // NEW CHECK: Check if email already exists
        String checkEmailQuery = "SELECT COUNT(*) AS count FROM customers WHERE Email = ?";
        ObservableList<Integer> emailCount = databaseConfig.fetchDataWithParams(checkEmailQuery, rs -> {
            return rs.getInt("count");
        }, pstmt -> {
            pstmt.setString(1, emailField.getText().trim());
        });

        if (!emailCount.isEmpty() && emailCount.get(0) > 0) {
            // Email already exists
            FXUtils.setFieldError(emailField, "This email address is already in use.");
            return false;
        }

        String insertQuery = "INSERT INTO customers (FirstName, LastName, Email, Phone, Address, City, State, ZipCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        boolean isInserted = databaseConfig.executeUpdate(insertQuery, pstmt -> {
            pstmt.setString(1, firstNameField.getText().trim());
            pstmt.setString(2, lastNameField.getText().trim());
            pstmt.setString(3, emailField.getText().trim());
            pstmt.setString(4, phoneField.getText().trim());
            pstmt.setString(5, addressField.getText().trim());
            pstmt.setString(6, cityField.getText().trim());
            pstmt.setString(7, stateField.getText().trim());
            pstmt.setString(8, zipCodeField.getText().trim());
        });

        if (isInserted) {
            CustomAlerts.showInformation("Customer added successfully!");
            FXUtils.resetFieldStyles(firstNameField, lastNameField, emailField, phoneField, addressField, cityField,
                    stateField, zipCodeField);

            firstNameField.clear();
            lastNameField.clear();
            emailField.clear();
            phoneField.clear();
            addressField.clear();
            cityField.clear();
            stateField.clear();
            zipCodeField.clear();
        } else {
            CustomAlerts.showError("Failed to add customer. Please try again.");
        }

        return isInserted;
    }

    public static boolean update(CustomField customerIDField, CustomField firstNameField, CustomField lastNameField,
            CustomField emailField, CustomField phoneField, CustomField addressField,
            CustomField cityField, CustomField stateField, CustomField zipCodeField) {

        boolean valid = true;

        if (!FXUtils.checkNullField(customerIDField))
            valid = false;
        if (!FXUtils.checkNumericField(customerIDField))
            valid = false;

        if (!FXUtils.checkNullField(firstNameField))
            valid = false;

        if (!FXUtils.checkNullField(lastNameField))
            valid = false;

        if (!FXUtils.checkNullField(emailField))
            valid = false;
        if (!FXUtils.isValidEmail(emailField))
            valid = false;

        if (!FXUtils.checkNullField(phoneField))
            valid = false;
        if (!FXUtils.isValidPhone(phoneField))
            valid = false;

        if (!FXUtils.checkNullField(addressField))
            valid = false;
        if (!FXUtils.checkNullField(cityField))
            valid = false;
        if (!FXUtils.checkNullField(stateField))
            valid = false;

        if (!FXUtils.checkNullField(zipCodeField))
            valid = false;
        if (!FXUtils.checkNumericField(zipCodeField))
            valid = false;

        if (!valid) {
            return false;
        }

        // Check if this email is used by another customer than the one being updated
        String checkEmailQuery = "SELECT COUNT(*) AS count FROM customers WHERE Email = ? AND CustomerID <> ?";
        ObservableList<Integer> emailCount = databaseConfig.fetchDataWithParams(checkEmailQuery, rs -> {
            return rs.getInt("count");
        }, pstmt -> {
            pstmt.setString(1, emailField.getText().trim());
            pstmt.setInt(2, Integer.parseInt(customerIDField.getText().trim()));
        });

        if (!emailCount.isEmpty() && emailCount.get(0) > 0) {
            // Email already in use by another customer
            FXUtils.setFieldError(emailField, "This email address is already in use.");
            return false;
        }

        String updateQuery = "UPDATE customers SET FirstName = ?, LastName = ?, Email = ?, Phone = ?, Address = ?, City = ?, State = ?, ZipCode = ? WHERE CustomerID = ?";

        boolean isUpdated = databaseConfig.executeUpdate(updateQuery, pstmt -> {
            pstmt.setString(1, firstNameField.getText().trim());
            pstmt.setString(2, lastNameField.getText().trim());
            pstmt.setString(3, emailField.getText().trim());
            pstmt.setString(4, phoneField.getText().trim());
            pstmt.setString(5, addressField.getText().trim());
            pstmt.setString(6, cityField.getText().trim());
            pstmt.setString(7, stateField.getText().trim());
            pstmt.setString(8, zipCodeField.getText().trim());
            pstmt.setInt(9, Integer.parseInt(customerIDField.getText().trim()));
        });

        if (isUpdated) {
            CustomAlerts.showInformation("Customer updated successfully!");
            FXUtils.resetFieldStyles(customerIDField, firstNameField, lastNameField, emailField, phoneField,
                    addressField, cityField, stateField, zipCodeField);

            customerIDField.clear();
            firstNameField.clear();
            lastNameField.clear();
            emailField.clear();
            phoneField.clear();
            addressField.clear();
            cityField.clear();
            stateField.clear();
            zipCodeField.clear();
        } else {
            CustomAlerts.showError("Failed to update customer. Please try again.");
        }

        return isUpdated;
    }
}
