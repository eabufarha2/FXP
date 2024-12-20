package com.example.ui;

import com.example.components.CustomAlerts;
import com.example.components.CustomBorderPane;
import com.example.components.CustomButtom;
import com.example.components.CustomField;
import com.example.components.CustomHBox;
import com.example.components.CustomVBox;
import com.example.controller.CarsController;
import com.example.model.Car;
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

public class CarsView extends ScrollPane {
    private ObservableList<Car> masterData;
    private FilteredList<Car> filteredData;
    private TableView<Car> tableView;

    private CustomField searchCarIDField = new CustomField("Search Car ID");
    private CustomField searchMakeField = new CustomField("Search Make");
    private CustomField searchModelField = new CustomField("Search Model");
    private CustomField searchYearField = new CustomField("Search Year");
    private CustomField searchPriceField = new CustomField("Search Price");
    private CustomField searchStockField = new CustomField("Search Stock");
    private CustomField searchVinField = new CustomField("Search VIN");

    private CustomField formMakeField = new CustomField("Make");
    private CustomField formModelField = new CustomField("Model");
    private CustomField formYearField = new CustomField("Year");
    private CustomField formPriceField = new CustomField("Price");
    private CustomField formStockField = new CustomField("Stock");
    private CustomField formVinField = new CustomField("VIN");

    private CustomButtom insertButton = new CustomButtom("Insert");
    private CustomButtom updateButton = new CustomButtom("Update");
    private CustomButtom clearButton = new CustomButtom("Clear");
    private CustomButtom reportButton = new CustomButtom("Report");

    private CustomField hiddenCarIDField = new CustomField();

    private CarsController carsController;

    public CarsView(CustomHBox viewContainer) {
        CustomBorderPane boarderPane = new CustomBorderPane();
        boarderPane.getStyleClass().add("boarderPane");
        boarderPane.prefWidthProperty().bind(this.widthProperty().subtract(40));
        boarderPane.prefHeightProperty().bind(this.heightProperty().subtract(40));

        getStyleClass().add("content");
        prefWidthProperty().bind(viewContainer.widthProperty().multiply(0.95));
        getStylesheets().add(getClass().getResource("/Styles/cars.css").toExternalForm());

        carsController = new CarsController();
        masterData = carsController.getCarsUtil().cars;

        filteredData = new FilteredList<>(masterData, p -> true);

        CustomVBox mainContainer = new CustomVBox("main-container", Pos.CENTER);

        boarderPane.setCenter(mainContainer);

        CustomHBox searchBox = createSearchBox();
        tableView = createTableView();
        tableView.setItems(filteredData);

        CustomVBox formBox = createFormBox();

        mainContainer.getChildren().addAll(searchBox, tableView, formBox);
        setContent(boarderPane);
    }

    private CustomHBox createSearchBox() {
        CustomHBox searchBox = new CustomHBox("search-box", Pos.CENTER_LEFT);
        searchBox.getChildren().addAll(
                searchCarIDField,
                searchMakeField,
                searchModelField,
                searchYearField,
                searchPriceField,
                searchStockField,
                searchVinField);

        searchCarIDField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchMakeField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchModelField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchYearField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchPriceField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchStockField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());
        searchVinField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter());

        return searchBox;
    }

    private TableView<Car> createTableView() {
        TableView<Car> table = new TableView<>();
        table.setPrefHeight(400);

        TableColumn<Car, Number> carIDCol = new TableColumn<>("Car ID");
        carIDCol.setCellValueFactory(cellData -> cellData.getValue().carIDProperty());
        carIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Car, String> makeCol = new TableColumn<>("Make");
        makeCol.setCellValueFactory(cellData -> cellData.getValue().makeProperty());
        makeCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Car, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        modelCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Car, Number> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        yearCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Car, Number> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        priceCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Car, Number> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(cellData -> cellData.getValue().stockProperty());
        stockCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Car, String> vinCol = new TableColumn<>("VIN");
        vinCol.setCellValueFactory(cellData -> cellData.getValue().vinProperty());
        vinCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(carIDCol, makeCol, modelCol, yearCol, priceCol, stockCol, vinCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSortOrder().add(carIDCol);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });

        SortedList<Car> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        return table;
    }

    private CustomVBox createFormBox() {
        CustomVBox formContainer = new CustomVBox("form-container", Pos.CENTER);

        CustomHBox formBox = new CustomHBox("form-box", Pos.CENTER);
        formBox.setSpacing(10);

        hiddenCarIDField.setVisible(false);
        hiddenCarIDField.setManaged(false);

        formBox.getChildren().addAll(
                hiddenCarIDField,
                formMakeField,
                formModelField,
                formYearField,
                formPriceField,
                formStockField,
                formVinField);

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
        filteredData.setPredicate(car -> {
            boolean matchesCarID = searchCarIDField.getText().isEmpty() ||
                    String.valueOf(car.getCarID()).contains(searchCarIDField.getText().trim());
            boolean matchesMake = searchMakeField.getText().isEmpty() ||
                    car.getMake().toLowerCase().contains(searchMakeField.getText().toLowerCase().trim());
            boolean matchesModel = searchModelField.getText().isEmpty() ||
                    car.getModel().toLowerCase().contains(searchModelField.getText().toLowerCase().trim());
            boolean matchesYear = searchYearField.getText().isEmpty() ||
                    String.valueOf(car.getYear()).contains(searchYearField.getText().trim());
            boolean matchesPrice = searchPriceField.getText().isEmpty() ||
                    String.valueOf(car.getPrice()).contains(searchPriceField.getText().trim());
            boolean matchesStock = searchStockField.getText().isEmpty() ||
                    String.valueOf(car.getStock()).contains(searchStockField.getText().trim());
            boolean matchesVin = searchVinField.getText().isEmpty() ||
                    car.getVin().toLowerCase().contains(searchVinField.getText().toLowerCase().trim());

            return matchesCarID && matchesMake && matchesModel && matchesYear && matchesPrice && matchesStock
                    && matchesVin;
        });
    }

    private void populateForm(Car car) {
        hiddenCarIDField.setText(String.valueOf(car.getCarID()));
        formMakeField.setText(car.getMake());
        formModelField.setText(car.getModel());
        formYearField.setText(String.valueOf(car.getYear()));
        formPriceField.setText(String.valueOf(car.getPrice()));
        formStockField.setText(String.valueOf(car.getStock()));
        formVinField.setText(car.getVin());
    }

    private void handleInsert() {
        boolean success = carsController.insertCar(
                formMakeField,
                formModelField,
                formYearField,
                formPriceField,
                formStockField,
                formVinField);

        if (success) {
            masterData.setAll(carsController.getCarsUtil().cars);
            clearForm();
        }
    }

    private void handleUpdate() {
        if (hiddenCarIDField.getText().isEmpty()) {
            FXUtils.setButtonError(updateButton);
            updateButton.setTooltip(new Tooltip("No car selected for update."));
            return;
        }

        boolean success = carsController.updateCar(
                hiddenCarIDField,
                formMakeField,
                formModelField,
                formYearField,
                formPriceField,
                formStockField,
                formVinField);

        if (success) {
            masterData.setAll(carsController.getCarsUtil().cars);
            clearForm();
        }
    }

    private void handleReport() {
        Car selectedCar = tableView.getSelectionModel().getSelectedItem();
        if (selectedCar == null) {
            CustomAlerts.showError("No car selected. Please select a car first.");
            return;
        }
        int carID = selectedCar.getCarID();
        new ReportView("cars", carID);
    }

    private void clearForm() {
        hiddenCarIDField.clear();
        formMakeField.clear();
        formModelField.clear();
        formYearField.clear();
        formPriceField.clear();
        formStockField.clear();
        formVinField.clear();

        FXUtils.resetFieldStyles(formMakeField);
        FXUtils.resetFieldStyles(formModelField);
        FXUtils.resetFieldStyles(formYearField);
        FXUtils.resetFieldStyles(formPriceField);
        FXUtils.resetFieldStyles(formStockField);
        FXUtils.resetFieldStyles(formVinField);

        formMakeField.setPromptText("Make");
        formModelField.setPromptText("Model");
        formYearField.setPromptText("Year");
        formPriceField.setPromptText("Price");
        formStockField.setPromptText("Stock");
        formVinField.setPromptText("VIN");

        searchCarIDField.clear();
        searchMakeField.clear();
        searchModelField.clear();
        searchYearField.clear();
        searchPriceField.clear();
        searchStockField.clear();
        searchVinField.clear();

        searchCarIDField.setPromptText("Car ID");
        searchMakeField.setPromptText("Make");
        searchModelField.setPromptText("Model");
        searchYearField.setPromptText("Year");
        searchPriceField.setPromptText("Price");
        searchStockField.setPromptText("Stock");
        searchVinField.setPromptText("VIN");

        tableView.getSelectionModel().clearSelection();
        FXUtils.resetButtonStyles(insertButton);
        FXUtils.resetButtonStyles(updateButton);
        FXUtils.resetButtonStyles(clearButton);
        FXUtils.resetButtonStyles(reportButton);

        if (filteredData != null) {
            filteredData.setPredicate(null); // Show all data
        }
    }
}
