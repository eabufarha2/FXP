package com.example.ui;

import com.example.components.CustomAlerts;
import com.example.components.CustomBorderPane;
import com.example.components.CustomButtom;
import com.example.components.CustomField;
import com.example.components.CustomHBox;
import com.example.components.CustomVBox;
import com.example.controller.ServicesController;
import com.example.model.Service;
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

public class ServicesView extends ScrollPane {
    private ObservableList<Service> masterData;
    private FilteredList<Service> filteredData;
    private TableView<Service> tableView;

    private CustomField searchServiceIDField = new CustomField("Search Service ID");
    private CustomField searchCarIDField = new CustomField("Search Car ID");
    private CustomField searchCustomerIDField = new CustomField("Search Customer ID");
    private CustomField searchServiceDateField = new CustomField("Search Service Date");
    private CustomField searchServiceDescriptionField = new CustomField("Search Service Description");
    private CustomField searchCostField = new CustomField("Search Cost");

    private CustomField hiddenServiceIDField = new CustomField();
    private CustomField formCarIDField = new CustomField("Car ID");
    private CustomField formCustomerIDField = new CustomField("Customer ID");
    private CustomField formServiceDateField = new CustomField("Service Date");
    private CustomField formServiceDescriptionField = new CustomField("Service Description");
    private CustomField formCostField = new CustomField("Cost");

    private CustomButtom insertButton = new CustomButtom("Insert");
    private CustomButtom updateButton = new CustomButtom("Update");
    private CustomButtom clearButton = new CustomButtom("Clear");
    private CustomButtom reportButton = new CustomButtom("Report");

    private ServicesController servicesController;

    public ServicesView(CustomHBox viewContainer) {
        CustomBorderPane borderPane = new CustomBorderPane();
        borderPane.getStyleClass().add("boarderPane");
        borderPane.prefWidthProperty().bind(this.widthProperty().subtract(40));
        borderPane.prefHeightProperty().bind(this.heightProperty().subtract(40));

        getStyleClass().add("content");
        prefWidthProperty().bind(viewContainer.widthProperty().multiply(0.95));
        getStylesheets().add(getClass().getResource("/Styles/cars.css").toExternalForm());

        servicesController = new ServicesController();
        masterData = servicesController.getServicesUtil().services;

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
                searchServiceIDField,
                searchCarIDField,
                searchCustomerIDField,
                searchServiceDateField,
                searchServiceDescriptionField,
                searchCostField);

        searchServiceIDField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());
        searchCarIDField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());
        searchCustomerIDField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());
        searchServiceDateField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());
        searchServiceDescriptionField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());
        searchCostField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());

        return searchBox;
    }

    private TableView<Service> createTableView() {
        TableView<Service> table = new TableView<>();
        table.setPrefHeight(400);

        TableColumn<Service, Number> serviceIDCol = new TableColumn<>("Service ID");
        serviceIDCol.setCellValueFactory(cellData -> cellData.getValue().serviceIDProperty());
        serviceIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Service, Number> carIDCol = new TableColumn<>("Car ID");
        carIDCol.setCellValueFactory(cellData -> cellData.getValue().carIDProperty());
        carIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Service, Number> customerIDCol = new TableColumn<>("Customer ID");
        customerIDCol.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty());
        customerIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Service, String> serviceDateCol = new TableColumn<>("Service Date");
        serviceDateCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().serviceDateProperty().get() != null) {
                return new SimpleStringProperty(cellData.getValue().getServiceDate().toString());
            } else {
                return new SimpleStringProperty("");
            }
        });
        serviceDateCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Service, String> serviceDescriptionCol = new TableColumn<>("Description");
        serviceDescriptionCol.setCellValueFactory(cellData -> cellData.getValue().serviceDescriptionProperty());
        serviceDescriptionCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Service, Number> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory(cellData -> cellData.getValue().costProperty());
        costCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(serviceIDCol, carIDCol, customerIDCol, serviceDateCol, serviceDescriptionCol,
                costCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSortOrder().add(serviceIDCol);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });

        SortedList<Service> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        return table;
    }

    private CustomVBox createFormBox() {
        CustomVBox formContainer = new CustomVBox("form-container", Pos.CENTER);
        CustomHBox formBox = new CustomHBox("form-box", Pos.CENTER);
        formBox.setSpacing(10);

        hiddenServiceIDField.setVisible(false);
        hiddenServiceIDField.setManaged(false);

        formBox.getChildren().addAll(
                hiddenServiceIDField,
                formCarIDField,
                formCustomerIDField,
                formServiceDateField,
                formServiceDescriptionField,
                formCostField);

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
        filteredData.setPredicate(service -> {
            boolean matchesServiceID = searchServiceIDField.getText().isEmpty() ||
                    String.valueOf(service.getServiceID()).contains(searchServiceIDField.getText().trim());
            boolean matchesCarID = searchCarIDField.getText().isEmpty() ||
                    String.valueOf(service.getCarID()).contains(searchCarIDField.getText().trim());
            boolean matchesCustomerID = searchCustomerIDField.getText().isEmpty() ||
                    String.valueOf(service.getCustomerID()).contains(searchCustomerIDField.getText().trim());
            boolean matchesServiceDate = searchServiceDateField.getText().isEmpty() ||
                    (service.getServiceDate() != null
                            && service.getServiceDate().toString().contains(searchServiceDateField.getText().trim()));
            boolean matchesServiceDescription = searchServiceDescriptionField.getText().isEmpty() ||
                    service.getServiceDescription().toLowerCase()
                            .contains(searchServiceDescriptionField.getText().toLowerCase().trim());
            boolean matchesCost = searchCostField.getText().isEmpty() ||
                    String.valueOf(service.getCost()).contains(searchCostField.getText().trim());

            return matchesServiceID && matchesCarID && matchesCustomerID && matchesServiceDate
                    && matchesServiceDescription && matchesCost;
        });
    }

    private void populateForm(Service service) {
        hiddenServiceIDField.setText(String.valueOf(service.getServiceID()));
        formCarIDField.setText(String.valueOf(service.getCarID()));
        formCustomerIDField.setText(String.valueOf(service.getCustomerID()));
        formServiceDateField.setText(service.getServiceDate() == null ? "" : service.getServiceDate().toString());
        formServiceDescriptionField.setText(service.getServiceDescription());
        formCostField.setText(String.valueOf(service.getCost()));
    }

    private void handleInsert() {
        boolean success = servicesController.insertService(
                formCarIDField,
                formCustomerIDField,
                formServiceDateField,
                formServiceDescriptionField,
                formCostField);
        if (success) {
            masterData.setAll(servicesController.getServicesUtil().services);
            clearForm();
        }
    }

    private void handleUpdate() {
        if (hiddenServiceIDField.getText().isEmpty()) {
            FXUtils.setButtonError(updateButton);
            updateButton.setTooltip(new Tooltip("No service selected for update."));
            return;
        }
        boolean success = servicesController.updateService(
                hiddenServiceIDField,
                formCarIDField,
                formCustomerIDField,
                formServiceDateField,
                formServiceDescriptionField,
                formCostField);
        if (success) {
            masterData.setAll(servicesController.getServicesUtil().services);
            clearForm();
        }
    }

    private void handleReport() {
        // For services, no selection needed:
        new ReportView("services", -1);
    }

    private void clearForm() {
        hiddenServiceIDField.clear();
        formCarIDField.clear();
        formCustomerIDField.clear();
        formServiceDateField.clear();
        formServiceDescriptionField.clear();
        formCostField.clear();

        FXUtils.resetFieldStyles(formCarIDField, formCustomerIDField, formServiceDateField, formServiceDescriptionField,
                formCostField);

        formCarIDField.setPromptText("Car ID");
        formCustomerIDField.setPromptText("Customer ID");
        formServiceDateField.setPromptText("Service Date (YYYY-MM-DD)");
        formServiceDescriptionField.setPromptText("Service Description");
        formCostField.setPromptText("Cost");

        searchServiceIDField.clear();
        searchCarIDField.clear();
        searchCustomerIDField.clear();
        searchServiceDateField.clear();
        searchServiceDescriptionField.clear();
        searchCostField.clear();

        searchServiceIDField.setPromptText("Service ID");
        searchCarIDField.setPromptText("Car ID");
        searchCustomerIDField.setPromptText("Customer ID");
        searchServiceDateField.setPromptText("Service Date");
        searchServiceDescriptionField.setPromptText("Service Description");
        searchCostField.setPromptText("Cost");

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
