package com.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.config.databaseConfig;
import com.example.model.Customer;



public class Customers {
    public ObservableList<Customer> customers = FXCollections.observableArrayList();

    // Constructor: Reads all customers from the database
    public Customers() {
        String query = "SELECT * FROM customers";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL, 
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Address"),
                        rs.getString("City"),
                        rs.getString("State"),
                        rs.getString("ZipCode")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Static method to insert a new Customer into the database
    public static boolean insert(Customer customer) {
        String insertQuery = "INSERT INTO customers (CustomerID, FirstName, LastName, Email, Phone, Address, City, State, ZipCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL, 
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(insertQuery)) {

            pstmt.setInt(1, customer.getCustomerID());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getLastName());
            pstmt.setString(4, customer.getEmail());
            pstmt.setString(5, customer.getPhone());
            pstmt.setString(6, customer.getAddress());
            pstmt.setString(7, customer.getCity());
            pstmt.setString(8, customer.getState());
            pstmt.setString(9, customer.getZipCode());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
