package com.example.controller;

import com.example.components.CustomField;
import com.example.util.EmployeesUtil;

public class EmployeesController {
    private EmployeesUtil employeesUtil;

    public EmployeesController() {
        this.employeesUtil = new EmployeesUtil();
    }

    public boolean insertEmployee(CustomField firstNameField,
            CustomField lastNameField,
            CustomField positionField,
            CustomField salaryField,
            CustomField hireDateField) {
        boolean success = EmployeesUtil.insert(
                firstNameField,
                lastNameField,
                positionField,
                salaryField,
                hireDateField);

        if (success) {
            refreshData();
        }
        return success;
    }

    public boolean updateEmployee(CustomField employeeIDField,
            CustomField firstNameField,
            CustomField lastNameField,
            CustomField positionField,
            CustomField salaryField,
            CustomField hireDateField) {
        boolean success = EmployeesUtil.update(
                employeeIDField,
                firstNameField,
                lastNameField,
                positionField,
                salaryField,
                hireDateField);

        if (success) {
            refreshData();
        }

        return success;
    }

    public void refreshData() {
        employeesUtil.refresh();
    }

    public EmployeesUtil getEmployeesUtil() {
        return employeesUtil;
    }
}
