package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.config.databaseConfig;
import com.example.model.UserAccount;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReadUsers {

    public  ObservableList<UserAccount> userAccounts = FXCollections.observableArrayList();

    public ReadUsers() {
        String query = "SELECT * FROM user_account";
        try (Connection con = DriverManager.getConnection(databaseConfig.URL, databaseConfig.USER, databaseConfig.PASSWORD);
        PreparedStatement pstmt = con.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                userAccounts.add(new UserAccount(
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
