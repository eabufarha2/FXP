package com.example.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

import com.example.components.CustomAlerts;
import com.example.components.CustomField;
import com.example.config.databaseConfig;
import com.example.model.Employee;

public class EmployeesUtil {
    public ObservableList<Employee> employees;

    public EmployeesUtil() {
        refresh();
    }

    public void refresh() {
        String query = "SELECT * FROM employees";
        employees = databaseConfig.fetchData(query, rs -> {
            try {
                return new Employee(
                        rs.getInt("EmployeeID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Position"),
                        rs.getBigDecimal("Salary"),
                        rs.getDate("HireDate") != null ? rs.getDate("HireDate").toLocalDate() : null);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        if (employees == null) {
            employees = FXCollections.observableArrayList();
        }
    }

    public static boolean insert(CustomField firstNameField, CustomField lastNameField,
            CustomField positionField, CustomField salaryField, CustomField hireDateField) {

        boolean valid = true;

        if (!FXUtils.checkNullField(firstNameField))
            valid = false;
        if (!FXUtils.checkAlphaField(firstNameField))
            valid = false;

        if (!FXUtils.checkNullField(lastNameField))
            valid = false;
        if (!FXUtils.checkAlphaField(lastNameField))
            valid = false;

        if (!FXUtils.checkNullField(positionField))
            valid = false;
        if (!FXUtils.checkAlphaField(positionField))
            valid = false;

        if (!FXUtils.checkNullField(salaryField))
            valid = false;
        if (!FXUtils.checkIntOrDoubleField(salaryField))
            valid = false;

        if (!FXUtils.checkNullField(hireDateField))
            valid = false;
        if (!FXUtils.isValidDate(hireDateField))
            valid = false;

        if (!valid) {
            return false;
        }

        String insertQuery = "INSERT INTO employees (FirstName, LastName, Position, Salary, HireDate) VALUES (?, ?, ?, ?, ?)";
        boolean isInserted = databaseConfig.executeUpdate(insertQuery, pstmt -> {
            pstmt.setString(1, firstNameField.getText().trim());
            pstmt.setString(2, lastNameField.getText().trim());
            pstmt.setString(3, positionField.getText().trim());
            pstmt.setBigDecimal(4, new BigDecimal(salaryField.getText().trim()));
            pstmt.setDate(5, Date.valueOf(hireDateField.getText().trim()));
        });

        if (isInserted) {
            CustomAlerts.showInformation("Employee added successfully!");
            FXUtils.resetFieldStyles(firstNameField, lastNameField, positionField, salaryField, hireDateField);

            firstNameField.clear();
            lastNameField.clear();
            positionField.clear();
            salaryField.clear();
            hireDateField.clear();
        } else {
            CustomAlerts.showError("Failed to add employee. Please try again.");
        }

        return isInserted;
    }

    public static boolean update(CustomField employeeIDField, CustomField firstNameField, CustomField lastNameField,
            CustomField positionField, CustomField salaryField, CustomField hireDateField) {

        boolean valid = true;

        if (!FXUtils.checkNullField(employeeIDField))
            valid = false;
        if (!FXUtils.checkNumericField(employeeIDField))
            valid = false;

        if (!FXUtils.checkNullField(firstNameField))
            valid = false;
        if (!FXUtils.checkAlphaField(firstNameField))
            valid = false;

        if (!FXUtils.checkNullField(lastNameField))
            valid = false;
        if (!FXUtils.checkAlphaField(lastNameField))
            valid = false;

        if (!FXUtils.checkNullField(positionField))
            valid = false;
        if (!FXUtils.checkAlphaField(positionField))
            valid = false;

        if (!FXUtils.checkNullField(salaryField))
            valid = false;
        if (!FXUtils.checkIntOrDoubleField(salaryField))
            valid = false;

        if (!FXUtils.checkNullField(hireDateField))
            valid = false;
        if (!FXUtils.isValidDate(hireDateField))
            valid = false;

        if (!valid) {
            return false;
        }

        String updateQuery = "UPDATE employees SET FirstName = ?, LastName = ?, Position = ?, Salary = ?, HireDate = ? WHERE EmployeeID = ?";

        boolean isUpdated = databaseConfig.executeUpdate(updateQuery, pstmt -> {
            pstmt.setString(1, firstNameField.getText().trim());
            pstmt.setString(2, lastNameField.getText().trim());
            pstmt.setString(3, positionField.getText().trim());
            pstmt.setBigDecimal(4, new BigDecimal(salaryField.getText().trim()));
            pstmt.setDate(5, Date.valueOf(hireDateField.getText().trim()));
            pstmt.setInt(6, Integer.parseInt(employeeIDField.getText().trim()));
        });

        if (isUpdated) {
            CustomAlerts.showInformation("Employee updated successfully!");
            FXUtils.resetFieldStyles(employeeIDField, firstNameField, lastNameField, positionField, salaryField,
                    hireDateField);

            employeeIDField.clear();
            firstNameField.clear();
            lastNameField.clear();
            positionField.clear();
            salaryField.clear();
            hireDateField.clear();
        } else {
            CustomAlerts.showError("Failed to update employee. Please try again.");
        }

        return isUpdated;
    }
}
