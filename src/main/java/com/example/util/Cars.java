package com.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.config.databaseConfig;
import com.example.model.Car;


public class Cars {
    public ObservableList<Car> cars = FXCollections.observableArrayList();

    // Constructor: Reads all cars from the database
    public Cars() {
        String query = "SELECT * FROM cars";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL, 
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("CarID"),
                        rs.getString("Make"),
                        rs.getString("Model"),
                        rs.getInt("Year"),
                        rs.getBigDecimal("Price"),
                        rs.getInt("Stock"),
                        rs.getString("VIN")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Static method to insert a new Car into the database
    public static boolean insert(Car car) {
        String insertQuery = "INSERT INTO cars (CarID, Make, Model, Year, Price, Stock, VIN) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL, 
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(insertQuery)) {

            pstmt.setInt(1, car.getCarID());
            pstmt.setString(2, car.getMake());
            pstmt.setString(3, car.getModel());
            pstmt.setInt(4, car.getYear());
            pstmt.setBigDecimal(5, car.getPrice());
            pstmt.setInt(6, car.getStock());
            pstmt.setString(7, car.getVin());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}