package com.example.controller;

import com.example.components.CustomField;
import com.example.util.CustomersUtil;

public class CustomersController {
    private CustomersUtil customersUtil;

    public CustomersController() {
        this.customersUtil = new CustomersUtil();
    }

    public boolean insertCustomer(CustomField firstNameField,
            CustomField lastNameField,
            CustomField emailField,
            CustomField phoneField,
            CustomField addressField,
            CustomField cityField,
            CustomField stateField,
            CustomField zipCodeField) {
        boolean success = CustomersUtil.insert(
                firstNameField,
                lastNameField,
                emailField,
                phoneField,
                addressField,
                cityField,
                stateField,
                zipCodeField);

        if (success) {
            refreshData();
        }
        return success;
    }

    public boolean updateCustomer(CustomField customerIDField,
            CustomField firstNameField,
            CustomField lastNameField,
            CustomField emailField,
            CustomField phoneField,
            CustomField addressField,
            CustomField cityField,
            CustomField stateField,
            CustomField zipCodeField) {
        boolean success = CustomersUtil.update(
                customerIDField,
                firstNameField,
                lastNameField,
                emailField,
                phoneField,
                addressField,
                cityField,
                stateField,
                zipCodeField);

        if (success) {
            refreshData();
        }

        return success;
    }

    public void refreshData() {
        customersUtil.refresh();
    }

    public CustomersUtil getCustomersUtil() {
        return customersUtil;
    }
}
