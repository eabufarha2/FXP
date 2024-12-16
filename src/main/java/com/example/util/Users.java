package com.example.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.components.CustomAlerts;
import com.example.components.CustomField;
import com.example.components.CustomPassword;
import com.example.config.databaseConfig;
import com.example.model.UserAccount;
import com.example.security.PasswordUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;

public class Users {
    public ObservableList<UserAccount> userAccounts = FXCollections.observableArrayList();

    // Constructor: Reads all UserAccounts from the database
    public Users() {
        String query = "SELECT * FROM user_account";
        try (Connection con = DriverManager.getConnection(databaseConfig.URL, databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                userAccounts.add(new UserAccount(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Static method to insert a new UserAccount into the database
    public static boolean insert(CustomField firstNameField, CustomField lastNameField, CustomField usernameField,
            CustomPassword passwordField) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String insertQuery = "INSERT INTO user_account (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(databaseConfig.URL, databaseConfig.USER,
                databaseConfig.PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(insertQuery)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, username);
            pstmt.setString(4, PasswordUtils.hashPassword(password));

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                CustomAlerts.showInformation("Account created successfully!");
                firstNameField.clear();
                lastNameField.clear();
                usernameField.clear();
                passwordField.clear();
                return true;
            } else {
                CustomAlerts.showError("Failed to create account. Please try again.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CustomAlerts.showError("An error occurred while creating the account.");
            return false;
        }
    }
}