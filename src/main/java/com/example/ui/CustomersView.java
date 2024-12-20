package com.example.ui;

import com.example.components.CustomAlerts;
import com.example.components.CustomBorderPane;
import com.example.components.CustomButtom;
import com.example.components.CustomField;
import com.example.components.CustomHBox;
import com.example.components.CustomVBox;
import com.example.controller.CustomersController;
import com.example.model.Customer;
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

public class CustomersView extends ScrollPane {
    private ObservableList<Customer> masterData;
    private FilteredList<Customer> filteredData;
    private TableView<Customer> tableView;

    private CustomField searchCustomerIDField = new CustomField("Search Customer ID");
    private CustomField searchFirstNameField = new CustomField("Search First Name");
    private CustomField searchLastNameField = new CustomField("Search Last Name");
    private CustomField searchEmailField = new CustomField("Search Email");
    private CustomField searchPhoneField = new CustomField("Search Phone");
    private CustomField searchAddressField = new CustomField("Search Address");
    private CustomField searchCityField = new CustomField("Search City");
    private CustomField searchStateField = new CustomField("Search State");
    private CustomField searchZipCodeField = new CustomField("Search ZipCode");

    private CustomField formFirstNameField = new CustomField("First Name");
    private CustomField formLastNameField = new CustomField("Last Name");
    private CustomField formEmailField = new CustomField("Email");
    private CustomField formPhoneField = new CustomField("Phone");
    private CustomField formAddressField = new CustomField("Address");
    private CustomField formCityField = new CustomField("City");
    private CustomField formStateField = new CustomField("State");
    private CustomField formZipCodeField = new CustomField("ZipCode");

    private CustomButtom insertButton = new CustomButtom("Insert");
    private CustomButtom updateButton = new CustomButtom("Update");
    private CustomButtom clearButton = new CustomButtom("Clear");
    private CustomButtom reportButton = new CustomButtom("Report");

    private CustomField hiddenCustomerIDField = new CustomField();

    private CustomersController customersController;

    public CustomersView(CustomHBox viewContainer) {
        CustomBorderPane borderPane = new CustomBorderPane();
        borderPane.getStyleClass().add("boarderPane");
        borderPane.prefWidthProperty().bind(this.widthProperty().subtract(40));
        borderPane.prefHeightProperty().bind(this.heightProperty().subtract(40));

        getStyleClass().add("content");
        prefWidthProperty().bind(viewContainer.widthProperty().multiply(0.95));
        getStylesheets().add(getClass().getResource("/Styles/cars.css").toExternalForm());

        customersController = new CustomersController();
        masterData = customersController.getCustomersUtil().customers;
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
                searchCustomerIDField,
                searchFirstNameField,
                searchLastNameField,
                searchEmailField,
                searchPhoneField,
                searchAddressField,
                searchCityField,
                searchStateField,
                searchZipCodeField);

        searchCustomerIDField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchFirstNameField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchLastNameField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchEmailField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchPhoneField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchAddressField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchCityField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchStateField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchZipCodeField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());

        return searchBox;
    }

    private TableView<Customer> createTableView() {
        TableView<Customer> table = new TableView<>();
        table.setPrefHeight(400);

        TableColumn<Customer, Number> customerIDCol = new TableColumn<>("Customer ID");
        customerIDCol.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty());
        customerIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Customer, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        firstNameCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Customer, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        lastNameCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        emailCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Customer, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        phoneCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        addressCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Customer, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        cityCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Customer, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
        stateCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Customer, String> zipCodeCol = new TableColumn<>("ZipCode");
        zipCodeCol.setCellValueFactory(cellData -> cellData.getValue().zipCodeProperty());
        zipCodeCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(customerIDCol, firstNameCol, lastNameCol, emailCol, phoneCol, addressCol, cityCol,
                stateCol, zipCodeCol);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSortOrder().add(customerIDCol);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        return table;
    }

    private CustomVBox createFormBox() {
        CustomVBox formContainer = new CustomVBox("form-container", Pos.CENTER);

        CustomHBox formBox = new CustomHBox("form-box", Pos.CENTER);
        formBox.setSpacing(10);

        hiddenCustomerIDField.setVisible(false);
        hiddenCustomerIDField.setManaged(false);

        formBox.getChildren().addAll(
                hiddenCustomerIDField,
                formFirstNameField,
                formLastNameField,
                formEmailField,
                formPhoneField,
                formAddressField,
                formCityField,
                formStateField,
                formZipCodeField);

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
        filteredData.setPredicate(customer -> {
            boolean matchesID = searchCustomerIDField.getText().isEmpty() ||
                    String.valueOf(customer.getCustomerID()).contains(searchCustomerIDField.getText().trim());
            boolean matchesFirstName = searchFirstNameField.getText().isEmpty() ||
                    customer.getFirstName().toLowerCase().contains(searchFirstNameField.getText().toLowerCase().trim());
            boolean matchesLastName = searchLastNameField.getText().isEmpty() ||
                    customer.getLastName().toLowerCase().contains(searchLastNameField.getText().toLowerCase().trim());
            boolean matchesEmail = searchEmailField.getText().isEmpty() ||
                    customer.getEmail().toLowerCase().contains(searchEmailField.getText().toLowerCase().trim());
            boolean matchesPhone = searchPhoneField.getText().isEmpty() ||
                    customer.getPhone().toLowerCase().contains(searchPhoneField.getText().toLowerCase().trim());
            boolean matchesAddress = searchAddressField.getText().isEmpty() ||
                    customer.getAddress().toLowerCase().contains(searchAddressField.getText().toLowerCase().trim());
            boolean matchesCity = searchCityField.getText().isEmpty() ||
                    customer.getCity().toLowerCase().contains(searchCityField.getText().toLowerCase().trim());
            boolean matchesState = searchStateField.getText().isEmpty() ||
                    customer.getState().toLowerCase().contains(searchStateField.getText().toLowerCase().trim());
            boolean matchesZipCode = searchZipCodeField.getText().isEmpty() ||
                    customer.getZipCode().toLowerCase().contains(searchZipCodeField.getText().toLowerCase().trim());

            return matchesID && matchesFirstName && matchesLastName && matchesEmail && matchesPhone && matchesAddress
                    && matchesCity && matchesState && matchesZipCode;
        });
    }

    private void populateForm(Customer customer) {
        hiddenCustomerIDField.setText(String.valueOf(customer.getCustomerID()));
        formFirstNameField.setText(customer.getFirstName());
        formLastNameField.setText(customer.getLastName());
        formEmailField.setText(customer.getEmail());
        formPhoneField.setText(customer.getPhone());
        formAddressField.setText(customer.getAddress());
        formCityField.setText(customer.getCity());
        formStateField.setText(customer.getState());
        formZipCodeField.setText(customer.getZipCode());
    }

    private void handleInsert() {
        boolean success = customersController.insertCustomer(
                formFirstNameField,
                formLastNameField,
                formEmailField,
                formPhoneField,
                formAddressField,
                formCityField,
                formStateField,
                formZipCodeField);

        if (success) {
            masterData.setAll(customersController.getCustomersUtil().customers);
            clearForm();
        }
    }

    private void handleUpdate() {
        if (hiddenCustomerIDField.getText().isEmpty()) {
            FXUtils.setButtonError(updateButton);
            updateButton.setTooltip(new Tooltip("No customer selected for update."));
            return;
        }
        boolean success = customersController.updateCustomer(
                hiddenCustomerIDField,
                formFirstNameField,
                formLastNameField,
                formEmailField,
                formPhoneField,
                formAddressField,
                formCityField,
                formStateField,
                formZipCodeField);

        if (success) {
            masterData.setAll(customersController.getCustomersUtil().customers);
            clearForm();
        }
    }

    private void handleReport() {
        Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            CustomAlerts.showError("No customer selected. Please select a customer first.");
            return;
        }
        int customerID = selectedCustomer.getCustomerID();
        new ReportView("customers", customerID);
    }

    private void clearForm() {
        hiddenCustomerIDField.clear();
        formFirstNameField.clear();
        formLastNameField.clear();
        formEmailField.clear();
        formPhoneField.clear();
        formAddressField.clear();
        formCityField.clear();
        formStateField.clear();
        formZipCodeField.clear();

        FXUtils.resetFieldStyles(formFirstNameField, formLastNameField, formEmailField, formPhoneField,
                formAddressField,
                formCityField, formStateField, formZipCodeField);

        formFirstNameField.setPromptText("First Name");
        formLastNameField.setPromptText("Last Name");
        formEmailField.setPromptText("Email");
        formPhoneField.setPromptText("Phone");
        formAddressField.setPromptText("Address");
        formCityField.setPromptText("City");
        formStateField.setPromptText("State");
        formZipCodeField.setPromptText("ZipCode");

        searchCustomerIDField.clear();
        searchFirstNameField.clear();
        searchLastNameField.clear();
        searchEmailField.clear();
        searchPhoneField.clear();
        searchAddressField.clear();
        searchCityField.clear();
        searchStateField.clear();
        searchZipCodeField.clear();

        searchCustomerIDField.setPromptText("Customer ID");
        searchFirstNameField.setPromptText("First Name");
        searchLastNameField.setPromptText("Last Name");
        searchEmailField.setPromptText("Email");
        searchPhoneField.setPromptText("Phone");
        searchAddressField.setPromptText("Address");
        searchCityField.setPromptText("City");
        searchStateField.setPromptText("State");
        searchZipCodeField.setPromptText("ZipCode");

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
