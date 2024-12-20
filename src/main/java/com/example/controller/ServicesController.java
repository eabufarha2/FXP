package com.example.controller;

import com.example.components.CustomField;
import com.example.util.ServicesUtil;

public class ServicesController {
    private ServicesUtil servicesUtil;

    public ServicesController() {
        this.servicesUtil = new ServicesUtil();
    }

    public boolean insertService(
            CustomField carIDField,
            CustomField customerIDField,
            CustomField serviceDateField,
            CustomField serviceDescriptionField,
            CustomField costField) {

        boolean success = ServicesUtil.insert(
                carIDField,
                customerIDField,
                serviceDateField,
                serviceDescriptionField,
                costField);

        if (success) {
            refreshData();
        }

        return success;
    }

    public boolean updateService(
            CustomField serviceIDField,
            CustomField carIDField,
            CustomField customerIDField,
            CustomField serviceDateField,
            CustomField serviceDescriptionField,
            CustomField costField) {

        boolean success = ServicesUtil.update(
                serviceIDField,
                carIDField,
                customerIDField,
                serviceDateField,
                serviceDescriptionField,
                costField);

        if (success) {
            refreshData();
        }

        return success;
    }

    public void refreshData() {
        servicesUtil.refresh();
    }

    public ServicesUtil getServicesUtil() {
        return servicesUtil;
    }
}
