package com.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.sql.SQLException;

import com.example.components.CustomAlerts;
import com.example.components.CustomField;
import com.example.config.databaseConfig;
import com.example.model.Payment;

public class PaymentsUtil {
    public ObservableList<Payment> payments;

    public PaymentsUtil() {
        refresh();
    }

    public void refresh() {
        String query = "SELECT * FROM payments";
        payments = databaseConfig.fetchData(query, rs -> {
            try {
                return new Payment(
                        rs.getInt("PaymentID"),
                        rs.getInt("OrderID"),
                        rs.getDate("PaymentDate").toLocalDate(),
                        rs.getString("PaymentMethod"),
                        rs.getDouble("Amount"));
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        if (payments == null) {
            payments = FXCollections.observableArrayList();
        }
    }

    public static boolean insert(
            CustomField orderIDField,
            CustomField paymentDateField,
            CustomField paymentMethodField,
            CustomField amountField) {

        boolean valid = true;

        if (!FXUtils.checkNullField(orderIDField))
            valid = false;
        if (!FXUtils.checkNumericField(orderIDField))
            valid = false;

        if (!FXUtils.checkNullField(paymentDateField))
            valid = false;
        if (!FXUtils.isValidDate(paymentDateField))
            valid = false;

        if (!FXUtils.checkNullField(paymentMethodField))
            valid = false;
        if (!FXUtils.checkAlphaField(paymentMethodField))
            valid = false;

        if (!FXUtils.checkNullField(amountField))
            valid = false;
        if (!FXUtils.checkIntOrDoubleField(amountField))
            valid = false;

        if (!valid) {
            return false;
        }

        String insertQuery = "INSERT INTO payments (OrderID, PaymentDate, PaymentMethod, Amount) VALUES (?, ?, ?, ?)";

        boolean isInserted = databaseConfig.executeUpdate(insertQuery, pstmt -> {
            try {
                pstmt.setInt(1, Integer.parseInt(orderIDField.getText().trim()));
                pstmt.setDate(2, Date.valueOf(paymentDateField.getText().trim()));
                pstmt.setString(3, paymentMethodField.getText().trim());
                pstmt.setDouble(4, Double.parseDouble(amountField.getText().trim()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        if (isInserted) {
            FXUtils.resetFieldStyles(orderIDField, paymentDateField, paymentMethodField, amountField);
            orderIDField.clear();
            paymentDateField.clear();
            paymentMethodField.clear();
            amountField.clear();

            CustomAlerts.showInformation("Payment added successfully!");
        }

        return isInserted;
    }

    public static boolean update(
            CustomField paymentIDField,
            CustomField orderIDField,
            CustomField paymentDateField,
            CustomField paymentMethodField,
            CustomField amountField) {

        boolean valid = true;

        if (!FXUtils.checkNullField(paymentIDField))
            valid = false;
        if (!FXUtils.checkNumericField(paymentIDField))
            valid = false;

        if (!FXUtils.checkNullField(orderIDField))
            valid = false;
        if (!FXUtils.checkNumericField(orderIDField))
            valid = false;

        if (!FXUtils.checkNullField(paymentDateField))
            valid = false;
        if (!FXUtils.isValidDate(paymentDateField))
            valid = false;

        if (!FXUtils.checkNullField(paymentMethodField))
            valid = false;
        if (!FXUtils.checkAlphaField(paymentMethodField))
            valid = false;

        if (!FXUtils.checkNullField(amountField))
            valid = false;
        if (!FXUtils.checkIntOrDoubleField(amountField))
            valid = false;

        if (!valid) {
            return false;
        }

        String updateQuery = "UPDATE payments SET OrderID = ?, PaymentDate = ?, PaymentMethod = ?, Amount = ? WHERE PaymentID = ?";

        boolean isUpdated = databaseConfig.executeUpdate(updateQuery, pstmt -> {
            try {
                pstmt.setInt(1, Integer.parseInt(orderIDField.getText().trim()));
                pstmt.setDate(2, Date.valueOf(paymentDateField.getText().trim()));
                pstmt.setString(3, paymentMethodField.getText().trim());
                pstmt.setDouble(4, Double.parseDouble(amountField.getText().trim()));
                pstmt.setInt(5, Integer.parseInt(paymentIDField.getText().trim()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        if (isUpdated) {
            CustomAlerts.showInformation("Payment updated successfully!");

            FXUtils.resetFieldStyles(paymentIDField, orderIDField, paymentDateField, paymentMethodField, amountField);
            paymentIDField.clear();
            orderIDField.clear();
            paymentDateField.clear();
            paymentMethodField.clear();
            amountField.clear();
        } else {
            CustomAlerts.showError("Failed to update payment. Please try again.");
        }

        return isUpdated;
    }
}
