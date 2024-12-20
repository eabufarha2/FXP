package com.example.ui;

import com.example.components.CustomAlerts;
import com.example.components.CustomBorderPane;
import com.example.components.CustomButtom;
import com.example.components.CustomField;
import com.example.components.CustomHBox;
import com.example.components.CustomVBox;
import com.example.controller.OrdersController;
import com.example.model.Order;
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

public class OrdersView extends ScrollPane {
    private ObservableList<Order> masterData;
    private FilteredList<Order> filteredData;
    private TableView<Order> tableView;

    private CustomField searchOrderIDField = new CustomField("Search Order ID");
    private CustomField searchOrderDateField = new CustomField("Search Order Date (YYYY-MM-DD)");
    private CustomField searchCarIDField = new CustomField("Search Car ID");
    private CustomField searchCustomerIDField = new CustomField("Search Customer ID");
    private CustomField searchEmployeeIDField = new CustomField("Search Employee ID");
    private CustomField searchQuantityField = new CustomField("Search Quantity");
    private CustomField searchTotalPriceField = new CustomField("Search Total Price");

    private CustomField formOrderDateField = new CustomField("Order Date (YYYY-MM-DD)");
    private CustomField formCarIDField = new CustomField("Car ID");
    private CustomField formCustomerIDField = new CustomField("Customer ID");
    private CustomField formEmployeeIDField = new CustomField("Employee ID");
    private CustomField formQuantityField = new CustomField("Quantity");
    private CustomField formTotalPriceField = new CustomField("Total Price");

    private CustomButtom insertButton = new CustomButtom("Insert");
    private CustomButtom updateButton = new CustomButtom("Update");
    private CustomButtom clearButton = new CustomButtom("Clear");
    private CustomButtom reportButton = new CustomButtom("Report");

    private CustomField hiddenOrderIDField = new CustomField();

    private OrdersController ordersController;

    public OrdersView(CustomHBox viewContainer) {
        CustomBorderPane borderPane = new CustomBorderPane();
        borderPane.getStyleClass().add("boarderPane");
        borderPane.prefWidthProperty().bind(this.widthProperty().subtract(40));
        borderPane.prefHeightProperty().bind(this.heightProperty().subtract(40));

        getStyleClass().add("content");
        prefWidthProperty().bind(viewContainer.widthProperty().multiply(0.95));
        getStylesheets().add(getClass().getResource("/Styles/cars.css").toExternalForm());

        ordersController = new OrdersController();
        masterData = ordersController.getOrdersUtil().orders;

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
                searchOrderIDField,
                searchOrderDateField,
                searchCarIDField,
                searchCustomerIDField,
                searchEmployeeIDField,
                searchQuantityField,
                searchTotalPriceField);

        searchOrderIDField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchOrderDateField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchCarIDField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchCustomerIDField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchEmployeeIDField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchQuantityField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchTotalPriceField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());

        return searchBox;
    }

    private TableView<Order> createTableView() {
        TableView<Order> table = new TableView<>();
        table.setPrefHeight(400);

        TableColumn<Order, Number> orderIDCol = new TableColumn<>("Order ID");
        orderIDCol.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty());
        orderIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Order, String> orderDateCol = new TableColumn<>("Order Date");
        orderDateCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().getOrderDate() != null) {
                return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getOrderDate().toString());
            } else {
                return new javafx.beans.property.SimpleStringProperty("");
            }
        });
        orderDateCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Order, Number> carIDCol = new TableColumn<>("Car ID");
        carIDCol.setCellValueFactory(cellData -> cellData.getValue().carIDProperty());
        carIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Order, Number> customerIDCol = new TableColumn<>("Customer ID");
        customerIDCol.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty());
        customerIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Order, Number> employeeIDCol = new TableColumn<>("Employee ID");
        employeeIDCol.setCellValueFactory(cellData -> cellData.getValue().employeeIDProperty());
        employeeIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Order, Number> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        quantityCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Order, String> totalPriceCol = new TableColumn<>("Total Price");
        totalPriceCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTotalPrice() != null) {
                return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTotalPrice().toString());
            } else {
                return new javafx.beans.property.SimpleStringProperty("");
            }
        });
        totalPriceCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(orderIDCol, orderDateCol, carIDCol, customerIDCol, employeeIDCol, quantityCol,
                totalPriceCol);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getSortOrder().add(orderIDCol);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });

        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        return table;
    }

    private CustomVBox createFormBox() {
        CustomVBox formContainer = new CustomVBox("form-container", Pos.CENTER);

        CustomHBox formBox = new CustomHBox("form-box", Pos.CENTER);
        formBox.setSpacing(10);

        hiddenOrderIDField.setVisible(false);
        hiddenOrderIDField.setManaged(false);

        formBox.getChildren().addAll(
                hiddenOrderIDField,
                formOrderDateField,
                formCarIDField,
                formCustomerIDField,
                formEmployeeIDField,
                formQuantityField,
                formTotalPriceField);

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
        filteredData.setPredicate(order -> {
            boolean matchesOrderID = searchOrderIDField.getText().isEmpty() ||
                    String.valueOf(order.getOrderID()).contains(searchOrderIDField.getText().trim());

            boolean matchesOrderDate = searchOrderDateField.getText().isEmpty() ||
                    (order.getOrderDate() != null &&
                            order.getOrderDate().toString().contains(searchOrderDateField.getText().trim()));

            boolean matchesCarID = searchCarIDField.getText().isEmpty() ||
                    String.valueOf(order.getCarID()).contains(searchCarIDField.getText().trim());

            boolean matchesCustomerID = searchCustomerIDField.getText().isEmpty() ||
                    String.valueOf(order.getCustomerID()).contains(searchCustomerIDField.getText().trim());

            boolean matchesEmployeeID = searchEmployeeIDField.getText().isEmpty() ||
                    (order.getEmployeeID() != null &&
                            String.valueOf(order.getEmployeeID()).contains(searchEmployeeIDField.getText().trim()));

            boolean matchesQuantity = searchQuantityField.getText().isEmpty() ||
                    String.valueOf(order.getQuantity()).contains(searchQuantityField.getText().trim());

            boolean matchesTotalPrice = searchTotalPriceField.getText().isEmpty() ||
                    (order.getTotalPrice() != null &&
                            order.getTotalPrice().toString().contains(searchTotalPriceField.getText().trim()));

            return matchesOrderID && matchesOrderDate && matchesCarID && matchesCustomerID &&
                    matchesEmployeeID && matchesQuantity && matchesTotalPrice;
        });
    }

    private void populateForm(Order order) {
        hiddenOrderIDField.setText(String.valueOf(order.getOrderID()));
        formOrderDateField.setText(order.getOrderDate() != null ? order.getOrderDate().toString() : "");
        formCarIDField.setText(String.valueOf(order.getCarID()));
        formCustomerIDField.setText(String.valueOf(order.getCustomerID()));
        formEmployeeIDField.setText(order.getEmployeeID() != null ? String.valueOf(order.getEmployeeID()) : "");
        formQuantityField.setText(String.valueOf(order.getQuantity()));
        formTotalPriceField.setText(order.getTotalPrice() != null ? order.getTotalPrice().toString() : "");
    }

    private void handleInsert() {
        boolean success = ordersController.insertOrder(
                formOrderDateField,
                formCarIDField,
                formCustomerIDField,
                formEmployeeIDField,
                formQuantityField,
                formTotalPriceField);

        if (success) {
            masterData.setAll(ordersController.getOrdersUtil().orders);
            clearForm();
        }
    }

    private void handleUpdate() {
        if (hiddenOrderIDField.getText().isEmpty()) {
            FXUtils.setButtonError(updateButton);
            updateButton.setTooltip(new Tooltip("No order selected for update."));
            return;
        }
        boolean success = ordersController.updateOrder(
                hiddenOrderIDField,
                formOrderDateField,
                formCarIDField,
                formCustomerIDField,
                formEmployeeIDField,
                formQuantityField,
                formTotalPriceField);

        if (success) {
            masterData.setAll(ordersController.getOrdersUtil().orders);
            clearForm();
        }
    }

    private void handleReport() {
        // Open the ReportView with "orders"
        new ReportView("orders", -1);
    }

    private void clearForm() {
        hiddenOrderIDField.clear();
        formOrderDateField.clear();
        formCarIDField.clear();
        formCustomerIDField.clear();
        formEmployeeIDField.clear();
        formQuantityField.clear();
        formTotalPriceField.clear();

        FXUtils.resetFieldStyles(formOrderDateField, formCarIDField, formCustomerIDField, formEmployeeIDField,
                formQuantityField, formTotalPriceField);

        formOrderDateField.setPromptText("Order Date (YYYY-MM-DD)");
        formCarIDField.setPromptText("Car ID");
        formCustomerIDField.setPromptText("Customer ID");
        formEmployeeIDField.setPromptText("Employee ID (Optional)");
        formQuantityField.setPromptText("Quantity");
        formTotalPriceField.setPromptText("Total Price");

        searchOrderIDField.clear();
        searchOrderDateField.clear();
        searchCarIDField.clear();
        searchCustomerIDField.clear();
        searchEmployeeIDField.clear();
        searchQuantityField.clear();
        searchTotalPriceField.clear();

        searchOrderIDField.setPromptText("Order ID");
        searchOrderDateField.setPromptText("Order Date (YYYY-MM-DD)");
        searchCarIDField.setPromptText("Car ID");
        searchCustomerIDField.setPromptText("Customer ID");
        searchEmployeeIDField.setPromptText("Employee ID");
        searchQuantityField.setPromptText("Quantity");
        searchTotalPriceField.setPromptText("Total Price");

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
