package com.example.controller;

import com.example.components.CustomField;
import com.example.util.OrdersUtil;

public class OrdersController {
    private OrdersUtil ordersUtil;

    public OrdersController() {
        this.ordersUtil = new OrdersUtil();
    }

    public boolean insertOrder(CustomField orderDateField,
            CustomField carIDField,
            CustomField customerIDField,
            CustomField employeeIDField,
            CustomField quantityField,
            CustomField totalPriceField) {
        boolean success = OrdersUtil.insert(
                orderDateField,
                carIDField,
                customerIDField,
                employeeIDField,
                quantityField,
                totalPriceField);

        if (success) {
            refreshData();
        }
        return success;
    }

    public boolean updateOrder(CustomField hiddenOrderIDField,
            CustomField orderDateField,
            CustomField carIDField,
            CustomField customerIDField,
            CustomField employeeIDField,
            CustomField quantityField,
            CustomField totalPriceField) {

        boolean success = OrdersUtil.update(
                hiddenOrderIDField,
                orderDateField,
                carIDField,
                customerIDField,
                employeeIDField,
                quantityField,
                totalPriceField);

        if (success) {
            refreshData();
        }

        return success;
    }

    public void refreshData() {
        ordersUtil.refresh();
    }

    public OrdersUtil getOrdersUtil() {
        return ordersUtil;
    }
}
