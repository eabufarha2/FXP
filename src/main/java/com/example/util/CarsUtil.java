package com.example.util;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.math.BigDecimal;
import java.sql.SQLException;

import com.example.components.CustomAlerts;
import com.example.components.CustomField;
import com.example.config.databaseConfig;
import com.example.model.Car;

public class CarsUtil {
    public ObservableList<Car> cars;

    public CarsUtil() {
        refresh();
    }

    public void refresh() {
        String query = "SELECT * FROM cars";
        cars = databaseConfig.fetchData(query, rs -> {
            try {
                return new Car(
                        rs.getInt("CarID"),
                        rs.getString("Make"),
                        rs.getString("Model"),
                        rs.getInt("Year"),
                        rs.getDouble("Price"),
                        rs.getInt("Stock"),
                        rs.getString("VIN"));
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        if (cars == null) {
            cars = FXCollections.observableArrayList();
        }
    }

    public static boolean insert(
            CustomField makeField,
            CustomField modelField,
            CustomField yearField,
            CustomField priceField,
            CustomField stockField,
            CustomField vinField) {
        FXUtils.resetFieldStyles(makeField, modelField, yearField, priceField, stockField, vinField);

        boolean valid = true;

        if (!FXUtils.checkNullField(makeField))
            valid = false;
        if (!FXUtils.checkAlphaField(makeField))
            valid = false;

        if (!FXUtils.checkNullField(modelField))
            valid = false;

        if (!FXUtils.checkNullField(yearField))
            valid = false;
        if (!FXUtils.checkNumericField(yearField))
            valid = false;

        if (!FXUtils.checkNullField(priceField))
            valid = false;
        if (!FXUtils.checkIntOrDoubleField(priceField))
            valid = false;

        if (!FXUtils.checkNullField(stockField))
            valid = false;
        if (!FXUtils.checkNumericField(stockField))
            valid = false;

        if (!FXUtils.checkNullField(vinField))
            valid = false;
        if (!FXUtils.checkVINField(vinField))
            valid = false;

        if (!valid) {
            return false;
        }

        // NEW CHECK: Check if VIN already exists
        String checkVinQuery = "SELECT COUNT(*) AS count FROM cars WHERE VIN = ?";
        ObservableList<Integer> vinCount = databaseConfig.fetchDataWithParams(checkVinQuery, rs -> {
            return rs.getInt("count");
        }, pstmt -> {
            pstmt.setString(1, vinField.getText().trim());
        });

        if (!vinCount.isEmpty() && vinCount.get(0) > 0) {
            // VIN already exists
            CustomAlerts.showError("A car with this VIN already exists. Please use a unique VIN.");
            return false;
        }

        // If VIN is unique, proceed with insert
        String insertQuery = "INSERT INTO cars (Make, Model, Year, Price, Stock, VIN) VALUES (?, ?, ?, ?, ?, ?)";

        boolean isInserted = databaseConfig.executeUpdate(insertQuery, pstmt -> {
            pstmt.setString(1, makeField.getText().trim());
            pstmt.setString(2, modelField.getText().trim());
            pstmt.setInt(3, Integer.parseInt(yearField.getText().trim()));
            pstmt.setBigDecimal(4, new BigDecimal(priceField.getText().trim()));
            pstmt.setInt(5, Integer.parseInt(stockField.getText().trim()));
            pstmt.setString(6, vinField.getText().trim());
        });

        if (isInserted) {
            CustomAlerts.showInformation("Car inserted successfully!");
            makeField.clear();
            modelField.clear();
            yearField.clear();
            priceField.clear();
            stockField.clear();
            vinField.clear();
        }

        return isInserted;
    }

    public static boolean update(
            CustomField carIDField,
            CustomField makeField,
            CustomField modelField,
            CustomField yearField,
            CustomField priceField,
            CustomField stockField,
            CustomField vinField) {
        


        boolean valid = true;

        if (!FXUtils.checkNullField(carIDField))
            valid = false;
        if (!FXUtils.checkNumericField(carIDField))
            valid = false;

        if (!FXUtils.checkNullField(makeField))
            valid = false;
        if (!FXUtils.checkAlphaField(makeField))
            valid = false;

        if (!FXUtils.checkNullField(modelField))
            valid = false;
        if (!FXUtils.checkAlphaField(modelField))
            valid = false;

        if (!FXUtils.checkNullField(yearField))
            valid = false;
        if (!FXUtils.checkYearField(yearField))
            valid = false;

        if (!FXUtils.checkNullField(priceField))
            valid = false;
        if (!FXUtils.checkIntOrDoubleField(priceField))
            valid = false;

        if (!FXUtils.checkNullField(stockField))
            valid = false;
        if (!FXUtils.checkNumericField(stockField))
            valid = false;

        if (!FXUtils.checkNullField(vinField))
            valid = false;
        if (!FXUtils.checkVINField(vinField))
            valid = false;

        if (!valid) {
            return false;
        }

        String updateQuery = "UPDATE cars SET Make = ?, Model = ?, Year = ?, Price = ?, Stock = ?, VIN = ? WHERE CarID = ?";

        boolean isUpdated = databaseConfig.executeUpdate(updateQuery, pstmt -> {
            pstmt.setString(1, makeField.getText().trim());
            pstmt.setString(2, modelField.getText().trim());
            pstmt.setInt(3, Integer.parseInt(yearField.getText().trim()));
            pstmt.setBigDecimal(4, new BigDecimal(priceField.getText().trim()));
            pstmt.setInt(5, Integer.parseInt(stockField.getText().trim()));
            pstmt.setString(6, vinField.getText().trim());
            pstmt.setInt(7, Integer.parseInt(carIDField.getText().trim()));
        });

        if (isUpdated) {
            CustomAlerts.showInformation("Car updated successfully!");

            FXUtils.resetFieldStyles(carIDField, makeField, modelField, yearField, priceField, stockField, vinField);
            carIDField.clear();
            makeField.clear();
            modelField.clear();
            yearField.clear();
            priceField.clear();
            stockField.clear();
            vinField.clear();
        } else {
            CustomAlerts.showError("Failed to update car. Please try again.");
        }

        return isUpdated;
    }
}
