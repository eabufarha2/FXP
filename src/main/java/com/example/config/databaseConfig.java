package com.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class databaseConfig {
    public static final String URL = "jdbc:mysql://localhost:3306/carscompany2";
    public static final String USER = "root";
    public static final String PASSWORD = "";

    public static <T> ObservableList<T> fetchData(String query, ResultSetHandler<T> handler) {
        ObservableList<T> dataList = FXCollections.observableArrayList();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                T obj = handler.handle(rs);
                if (obj != null)
                    dataList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    public static <T> ObservableList<T> fetchDataWithParams(String query, ResultSetHandler<T> handler,
            PreparedStatementSetter setter) {
        ObservableList<T> dataList = FXCollections.observableArrayList();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query)) {

            setter.setParameters(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    T obj = handler.handle(rs);
                    if (obj != null)
                        dataList.add(obj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static boolean executeUpdate(String query, PreparedStatementSetter setter) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query)) {

            setter.setParameters(pstmt);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void executeQuery(String query, PreparedStatementSetter setter, ResultSetHandler<Void> handler) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement pstmt = con.prepareStatement(query)) {
            setter.setParameters(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    handler.handle(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
