package com.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.config.databaseConfig;
import com.example.model.Employee;
import java.sql.*;

public class Employees {
    public ObservableList<Employee> employees = FXCollections.observableArrayList();

    // Constructor: Reads all employees from the database
    public Employees() {
        String query = "SELECT * FROM employees";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL,
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("EmployeeID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Position"),
                        rs.getBigDecimal("Salary"),
                        rs.getDate("HireDate") != null ? rs.getDate("HireDate").toLocalDate() : null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Static method to insert a new Employee into the database
    public static boolean insert(Employee employee) {
        String insertQuery = "INSERT INTO employees (EmployeeID, FirstName, LastName, Position, Salary, HireDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(
                databaseConfig.URL,
                databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(insertQuery)) {

            pstmt.setInt(1, employee.getEmployeeID());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getLastName());
            pstmt.setString(4, employee.getPosition());
            pstmt.setBigDecimal(5, employee.getSalary());
            pstmt.setDate(6, employee.getHireDate() != null ? Date.valueOf(employee.getHireDate()) : null);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
