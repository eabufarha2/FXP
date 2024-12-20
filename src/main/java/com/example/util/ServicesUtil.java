package com.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.sql.SQLException;

import com.example.components.CustomAlerts;
import com.example.components.CustomField;
import com.example.config.databaseConfig;
import com.example.model.Service;

public class ServicesUtil {
    public ObservableList<Service> services;

    public ServicesUtil() {
        refresh();
    }

    public void refresh() {
        String query = "SELECT * FROM services";
        services = databaseConfig.fetchData(query, rs -> {
            try {
                return new Service(
                        rs.getInt("ServiceID"),
                        rs.getInt("CarID"),
                        rs.getInt("CustomerID"),
                        rs.getDate("ServiceDate").toLocalDate(),
                        rs.getString("ServiceDescription"),
                        rs.getDouble("Cost"));
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        if (services == null) {
            services = FXCollections.observableArrayList();
        }
    }

    public static boolean insert(
            CustomField carIDField,
            CustomField customerIDField,
            CustomField serviceDateField,
            CustomField serviceDescriptionField,
            CustomField costField) {

        boolean valid = true;

        if (!FXUtils.checkNullField(carIDField))
            valid = false;
        if (!FXUtils.checkNumericField(carIDField))
            valid = false;

        if (!FXUtils.checkNullField(customerIDField))
            valid = false;
        if (!FXUtils.checkNumericField(customerIDField))
            valid = false;

        if (!FXUtils.checkNullField(serviceDateField))
            valid = false;
        if (!FXUtils.isValidDate(serviceDateField))
            valid = false;

        if (!FXUtils.checkNullField(serviceDescriptionField))
            valid = false;
        if (!FXUtils.checkAlphaField(serviceDescriptionField))
            valid = false;

        if (!FXUtils.checkNullField(costField))
            valid = false;
        if (!FXUtils.checkIntOrDoubleField(costField))
            valid = false;

        if (!valid) {
            return false;
        }

        String insertQuery = "INSERT INTO services (CarID, CustomerID, ServiceDate, ServiceDescription, Cost) VALUES (?, ?, ?, ?, ?)";

        boolean isInserted = databaseConfig.executeUpdate(insertQuery, pstmt -> {
            try {
                pstmt.setInt(1, Integer.parseInt(carIDField.getText().trim()));
                pstmt.setInt(2, Integer.parseInt(customerIDField.getText().trim()));
                pstmt.setDate(3, Date.valueOf(serviceDateField.getText().trim()));
                pstmt.setString(4, serviceDescriptionField.getText().trim());
                pstmt.setDouble(5, Double.parseDouble(costField.getText().trim()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        if (isInserted) {
            FXUtils.resetFieldStyles(carIDField, customerIDField, serviceDateField, serviceDescriptionField, costField);
            carIDField.clear();
            customerIDField.clear();
            serviceDateField.clear();
            serviceDescriptionField.clear();
            costField.clear();

            CustomAlerts.showInformation("Service added successfully!");
        } else {
            CustomAlerts.showError("Failed to add service. Please try again.");
        }

        return isInserted;
    }

    public static boolean update(
            CustomField serviceIDField,
            CustomField carIDField,
            CustomField customerIDField,
            CustomField serviceDateField,
            CustomField serviceDescriptionField,
            CustomField costField) {

        boolean valid = true;

        if (!FXUtils.checkNullField(serviceIDField))
            valid = false;
        if (!FXUtils.checkNumericField(serviceIDField))
            valid = false;

        if (!FXUtils.checkNullField(carIDField))
            valid = false;
        if (!FXUtils.checkNumericField(carIDField))
            valid = false;

        if (!FXUtils.checkNullField(customerIDField))
            valid = false;
        if (!FXUtils.checkNumericField(customerIDField))
            valid = false;

        if (!FXUtils.checkNullField(serviceDateField))
            valid = false;
        if (!FXUtils.isValidDate(serviceDateField))
            valid = false;

        if (!FXUtils.checkNullField(serviceDescriptionField))
            valid = false;
        if (!FXUtils.checkAlphaField(serviceDescriptionField))
            valid = false;

        if (!FXUtils.checkNullField(costField))
            valid = false;
        if (!FXUtils.checkIntOrDoubleField(costField))
            valid = false;

        if (!valid) {
            return false;
        }

        String updateQuery = "UPDATE services SET CarID = ?, CustomerID = ?, ServiceDate = ?, ServiceDescription = ?, Cost = ? WHERE ServiceID = ?";

        boolean isUpdated = databaseConfig.executeUpdate(updateQuery, pstmt -> {
            try {
                pstmt.setInt(1, Integer.parseInt(carIDField.getText().trim()));
                pstmt.setInt(2, Integer.parseInt(customerIDField.getText().trim()));
                pstmt.setDate(3, Date.valueOf(serviceDateField.getText().trim()));
                pstmt.setString(4, serviceDescriptionField.getText().trim());
                pstmt.setDouble(5, Double.parseDouble(costField.getText().trim()));
                pstmt.setInt(6, Integer.parseInt(serviceIDField.getText().trim()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        if (isUpdated) {
            CustomAlerts.showInformation("Service updated successfully!");

            FXUtils.resetFieldStyles(serviceIDField, carIDField, customerIDField, serviceDateField,
                    serviceDescriptionField, costField);
            serviceIDField.clear();
            carIDField.clear();
            customerIDField.clear();
            serviceDateField.clear();
            serviceDescriptionField.clear();
            costField.clear();
        } else {
            CustomAlerts.showError("Failed to update service. Please try again.");
        }

        return isUpdated;
    }
}
