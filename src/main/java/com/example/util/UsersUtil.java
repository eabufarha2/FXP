package com.example.util;

import java.sql.SQLException;
import com.example.components.CustomAlerts;
import com.example.components.CustomField;
import com.example.components.CustomPassword;
import com.example.config.databaseConfig;
import com.example.model.UserAccount;
import com.example.security.PasswordUtils;

import javafx.collections.ObservableList;

public class UsersUtil {
    public ObservableList<UserAccount> userAccounts;
    public static UserAccount activeUser;


    public UsersUtil() {
        String query = "SELECT * FROM user_account";
        userAccounts = databaseConfig.fetchData(query, rs -> {
            try {
                return new UserAccount(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"));
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    public static boolean insert(CustomField firstNameField, CustomField lastNameField,
            CustomField usernameField, CustomPassword passwordField) {

        boolean hasError = false;

        if (!FXUtils.checkNullField(firstNameField)) {
            hasError = true;
        }
        if (!FXUtils.checkNullField(lastNameField)) {
            hasError = true;
        }
        if (!FXUtils.checkNullField(usernameField)) {
            hasError = true;
        }
        if (!FXUtils.checkAlphaField(firstNameField)) {
            hasError = true;
        }
        if (!FXUtils.checkAlphaField(lastNameField)) {
            hasError = true;
        }
        if (!FXUtils.checkNullField(passwordField)) {
            hasError = true;
        }

        if (hasError) {
            return false;
        }

        String insertQuery = "INSERT INTO user_account (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";

        boolean isInserted = databaseConfig.executeUpdate(insertQuery, pstmt -> {
            try {
                pstmt.setString(1, firstNameField.getText().trim());
                pstmt.setString(2, lastNameField.getText().trim());
                pstmt.setString(3, usernameField.getText().trim());
                pstmt.setString(4, PasswordUtils.hashPassword(passwordField.getText().trim()));
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        if (isInserted) {
            FXUtils.resetFieldStyles(firstNameField);
            FXUtils.resetFieldStyles(lastNameField);
            FXUtils.resetFieldStyles(usernameField);
            FXUtils.resetFieldStyles(passwordField);

            firstNameField.clear();
            lastNameField.clear();
            usernameField.clear();
            passwordField.clear();
        } else {
            CustomAlerts.showError("Failed to create account. Please try again.");
        }

        return isInserted;
    }

    public static String getFullName() {
        return activeUser.getFirstName() + " " + activeUser.getLastName();
    }

    public static String getUsername() {
        return activeUser.getUsername();
    }

    public static String getPassword() {
        return activeUser.getPassword();
    }

    public static boolean updateActiveUser(String firstName, String lastName, String newHashedPassword) {
        if (activeUser == null) {
            return false;
        }

        String updateQuery = "UPDATE user_account SET first_name = ?, last_name = ?, password = ? WHERE username = ?";
        boolean updated = databaseConfig.executeUpdate(updateQuery, pstmt -> {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, newHashedPassword);
            pstmt.setString(4, activeUser.getUsername());
        });

        if (updated) {
            // Update the activeUser object in memory
            activeUser.setFirstName(firstName);
            activeUser.setLastName(lastName);
            activeUser.setPassword(newHashedPassword);
        }

        return updated;
    }

}
