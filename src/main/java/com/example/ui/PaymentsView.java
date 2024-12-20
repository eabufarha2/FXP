package com.example.ui;

import com.example.components.CustomAlerts;
import com.example.components.CustomBorderPane;
import com.example.components.CustomButtom;
import com.example.components.CustomField;
import com.example.components.CustomHBox;
import com.example.components.CustomVBox;
import com.example.controller.PaymentsController;
import com.example.model.Payment;
import com.example.util.FXUtils;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.beans.property.SimpleStringProperty;

public class PaymentsView extends ScrollPane {
    private ObservableList<Payment> masterData;
    private FilteredList<Payment> filteredData;
    private TableView<Payment> tableView;

    private CustomField searchPaymentIDField = new CustomField("Search Payment ID");
    private CustomField searchOrderIDField = new CustomField("Search Order ID");
    private CustomField searchPaymentDateField = new CustomField("Search Payment Date");
    private CustomField searchPaymentMethodField = new CustomField("Search Payment Method");
    private CustomField searchAmountField = new CustomField("Search Amount");

    private CustomField hiddenPaymentIDField = new CustomField();
    private CustomField formOrderIDField = new CustomField("Order ID");
    private CustomField formPaymentDateField = new CustomField("Payment Date");
    private CustomField formPaymentMethodField = new CustomField("Payment Method");
    private CustomField formAmountField = new CustomField("Amount");

    private CustomButtom insertButton = new CustomButtom("Insert");
    private CustomButtom updateButton = new CustomButtom("Update");
    private CustomButtom clearButton = new CustomButtom("Clear");
    private CustomButtom reportButton = new CustomButtom("Report");

    private PaymentsController paymentsController;

    public PaymentsView(CustomHBox viewContainer) {
        CustomBorderPane borderPane = new CustomBorderPane();
        borderPane.getStyleClass().add("boarderPane");
        borderPane.prefWidthProperty().bind(this.widthProperty().subtract(40));
        borderPane.prefHeightProperty().bind(this.heightProperty().subtract(40));

        getStyleClass().add("content");
        prefWidthProperty().bind(viewContainer.widthProperty().multiply(0.95));
        getStylesheets().add(getClass().getResource("/Styles/cars.css").toExternalForm());

        paymentsController = new PaymentsController();
        masterData = paymentsController.getPaymentsUtil().payments;

        filteredData = new FilteredList<>(masterData, p -> true);

        CustomVBox mainContainer = new CustomVBox("main-container", Pos.CENTER);
        borderPane.setCenter(mainContainer);

        CustomHBox searchBox = createSearchBox();
        tableView = createTableView();
        tableView.setItems(filteredData);
        CustomVBox formBox = createFormBox();

        mainContainer.getChildren().addAll(searchBox, tableView, formBox);
        setContent(borderPane);
    }

    private CustomHBox createSearchBox() {
        CustomHBox searchBox = new CustomHBox("search-box", Pos.CENTER_LEFT);
        searchBox.getChildren().addAll(
                searchPaymentIDField,
                searchOrderIDField,
                searchPaymentDateField,
                searchPaymentMethodField,
                searchAmountField);

        searchPaymentIDField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());
        searchOrderIDField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());
        searchPaymentDateField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());
        searchPaymentMethodField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());
        searchAmountField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());

        return searchBox;
    }

    private TableView<Payment> createTableView() {
        TableView<Payment> table = new TableView<>();
        table.setPrefHeight(400);

        TableColumn<Payment, Number> paymentIDCol = new TableColumn<>("Payment ID");
        paymentIDCol.setCellValueFactory(cellData -> cellData.getValue().paymentIDProperty());
        paymentIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Payment, Number> orderIDCol = new TableColumn<>("Order ID");
        orderIDCol.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty());
        orderIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Payment, String> paymentDateCol = new TableColumn<>("Payment Date");
        paymentDateCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().paymentDateProperty().get() != null) {
                return new SimpleStringProperty(cellData.getValue().getPaymentDate().toString());
            } else {
                return new SimpleStringProperty("");
            }
        });
        paymentDateCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Payment, String> paymentMethodCol = new TableColumn<>("Payment Method");
        paymentMethodCol.setCellValueFactory(cellData -> cellData.getValue().paymentMethodProperty());
        paymentMethodCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Payment, Number> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        amountCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(paymentIDCol, orderIDCol, paymentDateCol, paymentMethodCol, amountCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSortOrder().add(paymentIDCol);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });

        SortedList<Payment> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        return table;
    }

    private CustomVBox createFormBox() {
        CustomVBox formContainer = new CustomVBox("form-container", Pos.CENTER);
        CustomHBox formBox = new CustomHBox("form-box", Pos.CENTER);
        formBox.setSpacing(10);

        hiddenPaymentIDField.setVisible(false);
        hiddenPaymentIDField.setManaged(false);

        formBox.getChildren().addAll(
                hiddenPaymentIDField,
                formOrderIDField,
                formPaymentDateField,
                formPaymentMethodField,
                formAmountField);

        CustomHBox buttonBox = new CustomHBox("button-box", Pos.CENTER);
        buttonBox.getChildren().addAll(insertButton, updateButton, clearButton, reportButton);
        HBox.setHgrow(insertButton, Priority.ALWAYS);
        HBox.setHgrow(updateButton, Priority.ALWAYS);
        HBox.setHgrow(clearButton, Priority.ALWAYS);
        HBox.setHgrow(reportButton, Priority.ALWAYS);

        formContainer.getChildren().addAll(formBox, buttonBox);

        insertButton.setOnAction(e -> handleInsert());
        updateButton.setOnAction(e -> handleUpdate());
        clearButton.setOnAction(e -> clearForm());
        reportButton.setOnAction(e -> handleReport());

        return formContainer;
    }

    private void applyFilter() {
        filteredData.setPredicate(payment -> {
            boolean matchesPaymentID = searchPaymentIDField.getText().isEmpty() ||
                    String.valueOf(payment.getPaymentID()).contains(searchPaymentIDField.getText().trim());
            boolean matchesOrderID = searchOrderIDField.getText().isEmpty() ||
                    String.valueOf(payment.getOrderID()).contains(searchOrderIDField.getText().trim());
            boolean matchesPaymentDate = searchPaymentDateField.getText().isEmpty() ||
                    (payment.getPaymentDate() != null
                            && payment.getPaymentDate().toString().contains(searchPaymentDateField.getText().trim()));
            boolean matchesPaymentMethod = searchPaymentMethodField.getText().isEmpty() ||
                    payment.getPaymentMethod().toLowerCase()
                            .contains(searchPaymentMethodField.getText().toLowerCase().trim());
            boolean matchesAmount = searchAmountField.getText().isEmpty() ||
                    String.valueOf(payment.getAmount()).contains(searchAmountField.getText().trim());

            return matchesPaymentID && matchesOrderID && matchesPaymentDate && matchesPaymentMethod && matchesAmount;
        });
    }

    private void populateForm(Payment payment) {
        hiddenPaymentIDField.setText(String.valueOf(payment.getPaymentID()));
        formOrderIDField.setText(String.valueOf(payment.getOrderID()));
        formPaymentDateField.setText(payment.getPaymentDate() == null ? "" : payment.getPaymentDate().toString());
        formPaymentMethodField.setText(payment.getPaymentMethod());
        formAmountField.setText(String.valueOf(payment.getAmount()));
    }

    private void handleInsert() {
        boolean success = paymentsController.insertPayment(
                formOrderIDField,
                formPaymentDateField,
                formPaymentMethodField,
                formAmountField);
        if (success) {
            masterData.setAll(paymentsController.getPaymentsUtil().payments);
            clearForm();
        }
    }

    private void handleUpdate() {
        if (hiddenPaymentIDField.getText().isEmpty()) {
            FXUtils.setButtonError(updateButton);
            updateButton.setTooltip(new Tooltip("No payment selected for update."));
            return;
        }
        boolean success = paymentsController.updatePayment(
                hiddenPaymentIDField,
                formOrderIDField,
                formPaymentDateField,
                formPaymentMethodField,
                formAmountField);
        if (success) {
            masterData.setAll(paymentsController.getPaymentsUtil().payments);
            clearForm();
        }
    }

    private void handleReport() {
        // Open the ReportView with "payments"
        new ReportView("payments", -1);
    }

    private void clearForm() {
        hiddenPaymentIDField.clear();
        formOrderIDField.clear();
        formPaymentDateField.clear();
        formPaymentMethodField.clear();
        formAmountField.clear();

        FXUtils.resetFieldStyles(formOrderIDField, formPaymentDateField, formPaymentMethodField, formAmountField);

        formOrderIDField.setPromptText("Order ID");
        formPaymentDateField.setPromptText("Payment Date (YYYY-MM-DD)");
        formPaymentMethodField.setPromptText("Payment Method");
        formAmountField.setPromptText("Amount");

        searchPaymentIDField.clear();
        searchOrderIDField.clear();
        searchPaymentDateField.clear();
        searchPaymentMethodField.clear();
        searchAmountField.clear();

        searchPaymentIDField.setPromptText("Payment ID");
        searchOrderIDField.setPromptText("Order ID");
        searchPaymentDateField.setPromptText("Payment Date");
        searchPaymentMethodField.setPromptText("Payment Method");
        searchAmountField.setPromptText("Amount");

        tableView.getSelectionModel().clearSelection();
        FXUtils.resetButtonStyles(insertButton);
        FXUtils.resetButtonStyles(updateButton);
        FXUtils.resetButtonStyles(clearButton);
        FXUtils.resetButtonStyles(reportButton);

        if (filteredData != null) {
            filteredData.setPredicate(null);
        }
    }
}
