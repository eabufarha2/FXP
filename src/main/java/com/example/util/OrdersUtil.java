package com.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

import com.example.components.CustomAlerts;
import com.example.components.CustomField;
import com.example.config.databaseConfig;
import com.example.model.Order;

public class OrdersUtil {

    public ObservableList<Order> orders;

    public OrdersUtil() {
        refresh();
    }

    public void refresh() {
        String query = "SELECT * FROM orders";
        orders = databaseConfig.fetchData(query, rs -> {
            try {
                return new Order(
                        rs.getInt("OrderID"),
                        rs.getDate("OrderDate") != null ? rs.getDate("OrderDate").toLocalDate() : null,
                        rs.getInt("CarID"),
                        rs.getInt("CustomerID"),
                        rs.getObject("EmployeeID") != null ? rs.getInt("EmployeeID") : null,
                        rs.getInt("Quantity"),
                        rs.getBigDecimal("TotalPrice"));
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        if (orders == null) {
            orders = FXCollections.observableArrayList();
        }
    }

    public static boolean insert(CustomField orderDateField, CustomField carIDField,
            CustomField customerIDField, CustomField employeeIDField,
            CustomField quantityField, CustomField totalPriceField) {

        boolean valid = true;

        if (!FXUtils.checkNullField(orderDateField))
            valid = false;
        if (!FXUtils.isValidDate(orderDateField))
            valid = false;

        if (!FXUtils.checkNullField(carIDField))
            valid = false;
        if (!FXUtils.checkNumericField(carIDField))
            valid = false;

        if (!FXUtils.checkNullField(customerIDField))
            valid = false;
        if (!FXUtils.checkNumericField(customerIDField))
            valid = false;

        if (!FXUtils.checkNullField(employeeIDField))
            valid = false;
        if (!FXUtils.checkNumericField(employeeIDField))
            valid = false;

        if (!FXUtils.checkNullField(quantityField))
            valid = false;
        if (!FXUtils.checkNumericField(quantityField))
            valid = false;

        if (!FXUtils.checkNullField(totalPriceField))
            valid = false;
        if (!FXUtils.checkIntOrDoubleField(totalPriceField))
            valid = false;

        if (!valid) {
            return false;
        }

        String insertQuery = "INSERT INTO orders (OrderDate, CarID, CustomerID, EmployeeID, Quantity, TotalPrice) VALUES (?, ?, ?, ?, ?, ?)";
        boolean isInserted = databaseConfig.executeUpdate(insertQuery, pstmt -> {
            pstmt.setDate(1, Date.valueOf(orderDateField.getText().trim()));
            pstmt.setInt(2, Integer.parseInt(carIDField.getText().trim()));
            pstmt.setInt(3, Integer.parseInt(customerIDField.getText().trim()));
            pstmt.setInt(4, Integer.parseInt(employeeIDField.getText().trim()));
            pstmt.setInt(5, Integer.parseInt(quantityField.getText().trim()));
            pstmt.setBigDecimal(6, new BigDecimal(totalPriceField.getText().trim()));
        });

        if (isInserted) {
            CustomAlerts.showInformation("Order added successfully!");
            FXUtils.resetFieldStyles(orderDateField, carIDField, customerIDField, employeeIDField, quantityField,
                    totalPriceField);

            orderDateField.clear();
            carIDField.clear();
            customerIDField.clear();
            employeeIDField.clear();
            quantityField.clear();
            totalPriceField.clear();
        } else {
            CustomAlerts.showError("Failed to add order. Please try again.");
        }

        return isInserted;
    }

    public static boolean update(CustomField orderIDField, CustomField orderDateField, CustomField carIDField,
            CustomField customerIDField, CustomField employeeIDField,
            CustomField quantityField, CustomField totalPriceField) {

        boolean valid = true;

        if (!FXUtils.checkNullField(orderIDField))
            valid = false;
        if (!FXUtils.checkNumericField(orderIDField))
            valid = false;

        if (!FXUtils.checkNullField(orderDateField))
            valid = false;
        if (!FXUtils.isValidDate(orderDateField))
            valid = false;

        if (!FXUtils.checkNullField(carIDField))
            valid = false;
        if (!FXUtils.checkNumericField(carIDField))
            valid = false;

        if (!FXUtils.checkNullField(customerIDField))
            valid = false;
        if (!FXUtils.checkNumericField(customerIDField))
            valid = false;

        if (!FXUtils.checkNullField(employeeIDField))
            valid = false;
        if (!FXUtils.checkNumericField(employeeIDField))
            valid = false;

        if (!FXUtils.checkNullField(quantityField))
            valid = false;
        if (!FXUtils.checkNumericField(quantityField))
            valid = false;

        if (!FXUtils.checkNullField(totalPriceField))
            valid = false;
        if (!FXUtils.checkIntOrDoubleField(totalPriceField))
            valid = false;

        if (!valid) {
            return false;
        }

        String updateQuery = "UPDATE orders SET OrderDate = ?, CarID = ?, CustomerID = ?, EmployeeID = ?, Quantity = ?, TotalPrice = ? WHERE OrderID = ?";
        boolean isUpdated = databaseConfig.executeUpdate(updateQuery, pstmt -> {
            pstmt.setDate(1, Date.valueOf(orderDateField.getText().trim()));
            pstmt.setInt(2, Integer.parseInt(carIDField.getText().trim()));
            pstmt.setInt(3, Integer.parseInt(customerIDField.getText().trim()));
            pstmt.setInt(4, Integer.parseInt(employeeIDField.getText().trim()));
            pstmt.setInt(5, Integer.parseInt(quantityField.getText().trim()));
            pstmt.setBigDecimal(6, new BigDecimal(totalPriceField.getText().trim()));
            pstmt.setInt(7, Integer.parseInt(orderIDField.getText().trim()));
        });

        if (isUpdated) {
            CustomAlerts.showInformation("Order updated successfully!");
            FXUtils.resetFieldStyles(orderIDField, orderDateField, carIDField, customerIDField, employeeIDField,
                    quantityField, totalPriceField);

            orderIDField.clear();
            orderDateField.clear();
            carIDField.clear();
            customerIDField.clear();
            employeeIDField.clear();
            quantityField.clear();
            totalPriceField.clear();
        } else {
            CustomAlerts.showError("Failed to update order. Please try again.");
        }

        return isUpdated;
    }
}
