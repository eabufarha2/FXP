package com.example.controller;

import com.example.components.CustomAlerts;
import com.example.config.databaseConfig;
import com.example.config.PreparedStatementSetter;
import com.example.ui.ReportResultView;

import javafx.scene.control.ComboBox;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class ReportController {

    private static List<String[]> runQueryForTable(String query, PreparedStatementSetter setter) {
        List<String[]> tableData = new ArrayList<>();
        databaseConfig.executeQuery(query, setter, rs -> {
            try {
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();
                if (tableData.isEmpty()) {
                    String[] headers = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        headers[i - 1] = meta.getColumnLabel(i);
                    }
                    tableData.add(headers);
                }
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);
                    row[i - 1] = (value == null) ? "" : value.toString();
                }
                tableData.add(row);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        return tableData;
    }

    private static List<String[]> runQueryForTable(String query) {
        return runQueryForTable(query, pstmt -> {
        });
    }

    public static void generateReport(String entity, int selectedID, ComboBox<String> reportTypeCombo,
            ComboBox<String> periodCombo) {
        String selectedReport = reportTypeCombo.getValue();
        if (selectedReport == null || selectedReport.isEmpty()) {
            CustomAlerts.showError("Please select a report type.");
            return;
        }

        String selectedPeriod = (periodCombo != null && periodCombo.isVisible()) ? periodCombo.getValue() : null;
        List<String[]> tableData = null;

        try {
            switch (entity) {
                case "cars":
                    if ("Services done on this car".equals(selectedReport)) {
                        String query = "SELECT s.ServiceID, s.ServiceDate, s.ServiceDescription, s.Cost, c.FirstName AS CustomerFirstName, c.LastName AS CustomerLastName "
                                +
                                "FROM services s " +
                                "JOIN customers c ON s.CustomerID = c.CustomerID " +
                                "WHERE s.CarID = ? " +
                                "ORDER BY s.ServiceDate";
                        tableData = runQueryForTable(query, pstmt -> pstmt.setInt(1, selectedID));
                    }
                    break;

                case "customers":
                    if ("Services done for this customer".equals(selectedReport)) {
                        String query = "SELECT s.ServiceID, s.ServiceDate, s.ServiceDescription, s.Cost, ca.Make AS CarMake, ca.Model AS CarModel "
                                +
                                "FROM services s " +
                                "JOIN cars ca ON s.CarID = ca.CarID " +
                                "WHERE s.CustomerID = ? " +
                                "ORDER BY s.ServiceDate";
                        tableData = runQueryForTable(query, pstmt -> pstmt.setInt(1, selectedID));
                    } else if ("Payment methods and total paid by this customer".equals(selectedReport)) {
                        String query = "SELECT p.PaymentMethod, SUM(p.Amount) AS TotalPaid " +
                                "FROM payments p " +
                                "JOIN orders o ON p.OrderID = o.OrderID " +
                                "WHERE o.CustomerID = ? " +
                                "GROUP BY p.PaymentMethod";
                        tableData = runQueryForTable(query, pstmt -> pstmt.setInt(1, selectedID));
                    }
                    break;

                case "employees":
                    if ("Sales report by this employee".equals(selectedReport)) {
                        String query = "SELECT o.OrderID, o.OrderDate, ca.Make AS CarMake, ca.Model AS CarModel, o.Quantity, o.TotalPrice "
                                +
                                "FROM orders o " +
                                "JOIN cars ca ON o.CarID = ca.CarID " +
                                "WHERE o.EmployeeID = ? " +
                                "ORDER BY o.OrderDate";
                        tableData = runQueryForTable(query, pstmt -> pstmt.setInt(1, selectedID));
                    }
                    break;

                case "services":
                    if ("Revenue report by service (monthly or quarterly)".equals(selectedReport)) {
                        String query;
                        if ("Monthly".equals(selectedPeriod)) {
                            query = "SELECT s.ServiceDescription, DATE_FORMAT(s.ServiceDate, '%Y-%m') AS Period, SUM(s.Cost) AS Revenue "
                                    +
                                    "FROM services s " +
                                    "GROUP BY s.ServiceDescription, DATE_FORMAT(s.ServiceDate, '%Y-%m') " +
                                    "ORDER BY s.ServiceDescription, Period";
                        } else {
                            query = "SELECT s.ServiceDescription, CONCAT(YEAR(s.ServiceDate), '-Q', QUARTER(s.ServiceDate)) AS Period, SUM(s.Cost) AS Revenue "
                                    +
                                    "FROM services s " +
                                    "GROUP BY s.ServiceDescription, YEAR(s.ServiceDate), QUARTER(s.ServiceDate) " +
                                    "ORDER BY s.ServiceDescription, Period";
                        }
                        tableData = runQueryForTable(query);
                    } else if ("Services count done on each car".equals(selectedReport)) {
                        String query = "SELECT ca.Make, ca.Model, COUNT(s.ServiceID) as ServiceCount " +
                                "FROM services s " +
                                "JOIN cars ca ON s.CarID = ca.CarID " +
                                "GROUP BY ca.CarID, ca.Make, ca.Model " +
                                "ORDER BY ServiceCount DESC";
                        tableData = runQueryForTable(query);
                    }
                    break;

                case "orders":
                    if ("Monthly revenue from orders".equals(selectedReport)) {
                        String query = "SELECT DATE_FORMAT(OrderDate, '%Y-%m') AS Period, SUM(TotalPrice) AS TotalRevenue "
                                +
                                "FROM orders " +
                                "GROUP BY DATE_FORMAT(OrderDate, '%Y-%m') " +
                                "ORDER BY Period";
                        tableData = runQueryForTable(query);
                    }
                    break;

                case "payments":
                    if ("Total amounts paid by payment method".equals(selectedReport)) {
                        String query = "SELECT PaymentMethod, SUM(Amount) AS TotalPaid " +
                                "FROM payments " +
                                "GROUP BY PaymentMethod " +
                                "ORDER BY TotalPaid DESC";
                        tableData = runQueryForTable(query);
                    }
                    break;

                default:
                    CustomAlerts.showError("Unknown entity type for reporting.");
                    return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            CustomAlerts.showError("An error occurred while generating the report: " + ex.getMessage());
            return;
        }

        if (tableData == null || tableData.size() <= 1) {
            CustomAlerts.showInformation("No data found for the selected report.");
            return;
        }

        new ReportResultView(tableData);
    }
}
