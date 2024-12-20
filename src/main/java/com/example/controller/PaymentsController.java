package com.example.controller;

import com.example.components.CustomField;
import com.example.util.PaymentsUtil;

public class PaymentsController {
    private PaymentsUtil paymentsUtil;

    public PaymentsController() {
        this.paymentsUtil = new PaymentsUtil();
    }

    public boolean insertPayment(
            CustomField orderIDField,
            CustomField paymentDateField,
            CustomField paymentMethodField,
            CustomField amountField) {

        boolean success = PaymentsUtil.insert(
                orderIDField,
                paymentDateField,
                paymentMethodField,
                amountField);

        if (success) {
            refreshData();
        }

        return success;
    }

    public boolean updatePayment(
            CustomField paymentIDField,
            CustomField orderIDField,
            CustomField paymentDateField,
            CustomField paymentMethodField,
            CustomField amountField) {

        boolean success = PaymentsUtil.update(
                paymentIDField,
                orderIDField,
                paymentDateField,
                paymentMethodField,
                amountField);

        if (success) {
            refreshData();
        }

        return success;
    }

    public void refreshData() {
        paymentsUtil.refresh();
    }

    public PaymentsUtil getPaymentsUtil() {
        return paymentsUtil;
    }
}
