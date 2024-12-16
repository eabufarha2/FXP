package com.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.config.databaseConfig;
import com.example.model.*;

import com.example.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class Orders {
    public ObservableList<Order> orders = FXCollections.observableArrayList();

    // Constructor: Reads all orders from the database
    public Orders() {
        String query = "SELECT * FROM orders";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL, 
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("OrderID"),
                        rs.getDate("OrderDate").toLocalDate(),
                        rs.getInt("CarID"),
                        rs.getInt("CustomerID"),
                        rs.getObject("EmployeeID") != null ? rs.getInt("EmployeeID") : null,
                        rs.getInt("Quantity"),
                        rs.getBigDecimal("TotalPrice")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Static method to insert a new Order into the database
    public static boolean insert(Order order) {
        String insertQuery = "INSERT INTO orders (OrderID, OrderDate, CarID, CustomerID, EmployeeID, Quantity, TotalPrice) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL, 
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(insertQuery)) {

            pstmt.setInt(1, order.getOrderID());
            pstmt.setDate(2, Date.valueOf(order.getOrderDate()));
            pstmt.setInt(3, order.getCarID());
            pstmt.setInt(4, order.getCustomerID());
            pstmt.setObject(5, order.getEmployeeID(), Types.INTEGER); // Handles nullable EmployeeID
            pstmt.setInt(6, order.getQuantity());
            pstmt.setBigDecimal(7, order.getTotalPrice());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}