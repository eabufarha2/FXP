package com.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.config.databaseConfig;

import com.example.model.Service;


import java.sql.*;


public class Services {
    public ObservableList<Service> services = FXCollections.observableArrayList();

    // Constructor: Reads all services from the database
    public Services() {
        String query = "SELECT * FROM services";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL,
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                services.add(new Service(
                        rs.getInt("ServiceID"),
                        rs.getInt("CarID"),
                        rs.getInt("CustomerID"),
                        rs.getDate("ServiceDate").toLocalDate(),
                        rs.getString("ServiceDescription"),
                        rs.getBigDecimal("Cost")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Static method to insert a new Service into the database
    public static boolean insert(Service service) {
        String insertQuery = "INSERT INTO services (ServiceID, CarID, CustomerID, ServiceDate, ServiceDescription, Cost) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL,
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(insertQuery)) {

            pstmt.setInt(1, service.getServiceID());
            pstmt.setInt(2, service.getCarID());
            pstmt.setInt(3, service.getCustomerID());
            pstmt.setDate(4, Date.valueOf(service.getServiceDate()));
            pstmt.setString(5, service.getServiceDescription());
            pstmt.setBigDecimal(6, service.getCost());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
