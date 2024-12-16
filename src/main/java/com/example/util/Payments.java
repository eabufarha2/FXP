package com.example.util;

import com.example.config.databaseConfig;
import com.example.model.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


public class Payments {
    public ObservableList<Payment> payments = FXCollections.observableArrayList();

    public Payments() {
        String query = "SELECT * FROM payments";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL,
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                payments.add(new Payment(
                        rs.getInt("PaymentID"),
                        rs.getInt("OrderID"),
                        rs.getDate("PaymentDate").toLocalDate(),
                        rs.getString("PaymentMethod"),
                        rs.getBigDecimal("Amount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean insert(Payment payment) {
        String insertQuery = "INSERT INTO payments (PaymentID, OrderID, PaymentDate, PaymentMethod, Amount) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL,
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(insertQuery)) {

            pstmt.setInt(1, payment.getPaymentID());
            pstmt.setInt(2, payment.getOrderID());
            pstmt.setDate(3, Date.valueOf(payment.getPaymentDate()));
            pstmt.setString(4, payment.getPaymentMethod());
            pstmt.setBigDecimal(5, payment.getAmount());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}