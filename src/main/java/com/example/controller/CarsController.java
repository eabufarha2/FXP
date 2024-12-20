package com.example.controller;

import com.example.components.CustomField;
import com.example.util.CarsUtil;
import com.example.util.FXUtils;

public class CarsController {
    private CarsUtil carsUtil;

    public CarsController() {
        this.carsUtil = new CarsUtil();
    }

    public boolean insertCar(CustomField makeField,
            CustomField modelField,
            CustomField yearField,
            CustomField priceField,
            CustomField stockField,
            CustomField vinField) {
        boolean success = CarsUtil.insert(
                makeField,
                modelField,
                yearField,
                priceField,
                stockField,
                vinField);

        if (success) {
            refreshData();
        }
        return success;
    }

    public boolean updateCar(CustomField carIDField,
            CustomField makeField,
            CustomField modelField,
            CustomField yearField,
            CustomField priceField,
            CustomField stockField,
            CustomField vinField) {

        boolean success = CarsUtil.update(
                carIDField,
                makeField,
                modelField,
                yearField,
                priceField,
                stockField,
                vinField);

        if (success) {
            refreshData();
        }

        return success;
    }

    public void refreshData() {
        carsUtil.refresh();
    }

    public CarsUtil getCarsUtil() {
        return carsUtil;
    }
}
