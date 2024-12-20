package com.example.ui;

import com.example.components.CustomBorderPane;
import com.example.components.CustomButtom;
import com.example.components.CustomField;
import com.example.components.CustomHBox;
import com.example.components.CustomVBox;
import com.example.controller.EmployeesController;
import com.example.model.Employee;
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

public class EmployeesView extends ScrollPane {
    private ObservableList<Employee> masterData;
    private FilteredList<Employee> filteredData;
    private TableView<Employee> tableView;

    private CustomField searchEmployeeIDField = new CustomField("Search Employee ID");
    private CustomField searchFirstNameField = new CustomField("Search First Name");
    private CustomField searchLastNameField = new CustomField("Search Last Name");
    private CustomField searchPositionField = new CustomField("Search Position");
    private CustomField searchSalaryField = new CustomField("Search Salary");
    private CustomField searchHireDateField = new CustomField("Search Hire Date (YYYY-MM-DD)");

    private CustomField formFirstNameField = new CustomField("First Name");
    private CustomField formLastNameField = new CustomField("Last Name");
    private CustomField formPositionField = new CustomField("Position");
    private CustomField formSalaryField = new CustomField("Salary");
    private CustomField formHireDateField = new CustomField("Hire Date (YYYY-MM-DD)");

    private CustomButtom insertButton = new CustomButtom("Insert");
    private CustomButtom updateButton = new CustomButtom("Update");
    private CustomButtom clearButton = new CustomButtom("Clear");
    private CustomButtom reportButton = new CustomButtom("Report");

    private CustomField hiddenEmployeeIDField = new CustomField();

    private EmployeesController employeesController;

    public EmployeesView(CustomHBox viewContainer) {
        CustomBorderPane borderPane = new CustomBorderPane();
        borderPane.getStyleClass().add("boarderPane");
        borderPane.prefWidthProperty().bind(this.widthProperty().subtract(40));
        borderPane.prefHeightProperty().bind(this.heightProperty().subtract(40));

        getStyleClass().add("content");
        prefWidthProperty().bind(viewContainer.widthProperty().multiply(0.95));
        getStylesheets().add(getClass().getResource("/Styles/cars.css").toExternalForm());

        employeesController = new EmployeesController();
        masterData = employeesController.getEmployeesUtil().employees;
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
                searchEmployeeIDField,
                searchFirstNameField,
                searchLastNameField,
                searchPositionField,
                searchSalaryField,
                searchHireDateField);

        searchEmployeeIDField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchFirstNameField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchLastNameField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchPositionField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchSalaryField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());
        searchHireDateField.textProperty().addListener((o, oldVal, newVal) -> applyFilter());

        return searchBox;
    }

    private TableView<Employee> createTableView() {
        TableView<Employee> table = new TableView<>();
        table.setPrefHeight(400);

        TableColumn<Employee, Number> employeeIDCol = new TableColumn<>("Employee ID");
        employeeIDCol.setCellValueFactory(cellData -> cellData.getValue().employeeIDProperty());
        employeeIDCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Employee, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        firstNameCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Employee, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        lastNameCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Employee, String> positionCol = new TableColumn<>("Position");
        positionCol.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        positionCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Employee, Number> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(cellData -> {
            return new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getSalary().doubleValue());
        });
        salaryCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Employee, String> hireDateCol = new TableColumn<>("Hire Date");
        hireDateCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().getHireDate() != null) {
                return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getHireDate().toString());
            } else {
                return new javafx.beans.property.SimpleStringProperty("");
            }
        });
        hireDateCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(employeeIDCol, firstNameCol, lastNameCol, positionCol, salaryCol, hireDateCol);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSortOrder().add(employeeIDCol);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });

        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        return table;
    }

    private CustomVBox createFormBox() {
        CustomVBox formContainer = new CustomVBox("form-container", Pos.CENTER);

        CustomHBox formBox = new CustomHBox("form-box", Pos.CENTER);
        formBox.setSpacing(10);

        hiddenEmployeeIDField.setVisible(false);
        hiddenEmployeeIDField.setManaged(false);

        formBox.getChildren().addAll(
                hiddenEmployeeIDField,
                formFirstNameField,
                formLastNameField,
                formPositionField,
                formSalaryField,
                formHireDateField);

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
        filteredData.setPredicate(employee -> {
            String empIDFilter = searchEmployeeIDField.getText().trim();
            String fNameFilter = searchFirstNameField.getText().toLowerCase().trim();
            String lNameFilter = searchLastNameField.getText().toLowerCase().trim();
            String posFilter = searchPositionField.getText().toLowerCase().trim();
            String salaryFilter = searchSalaryField.getText().trim();
            String hireDateFilter = searchHireDateField.getText().trim();

            boolean matchesID = empIDFilter.isEmpty() ||
                    String.valueOf(employee.getEmployeeID()).contains(empIDFilter);
            boolean matchesFirstName = fNameFilter.isEmpty() ||
                    employee.getFirstName().toLowerCase().contains(fNameFilter);
            boolean matchesLastName = lNameFilter.isEmpty() ||
                    employee.getLastName().toLowerCase().contains(lNameFilter);
            boolean matchesPosition = posFilter.isEmpty() ||
                    employee.getPosition().toLowerCase().contains(posFilter);
            boolean matchesSalary = salaryFilter.isEmpty() ||
                    employee.getSalary().toPlainString().contains(salaryFilter);
            boolean matchesHireDate = hireDateFilter.isEmpty() ||
                    (employee.getHireDate() != null && employee.getHireDate().toString().contains(hireDateFilter));

            return matchesID && matchesFirstName && matchesLastName && matchesPosition && matchesSalary
                    && matchesHireDate;
        });
    }

    private void populateForm(Employee employee) {
        hiddenEmployeeIDField.setText(String.valueOf(employee.getEmployeeID()));
        formFirstNameField.setText(employee.getFirstName());
        formLastNameField.setText(employee.getLastName());
        formPositionField.setText(employee.getPosition());
        formSalaryField.setText(employee.getSalary().toPlainString());
        formHireDateField.setText(employee.getHireDate() != null ? employee.getHireDate().toString() : "");
    }

    private void handleInsert() {
        boolean success = employeesController.insertEmployee(
                formFirstNameField,
                formLastNameField,
                formPositionField,
                formSalaryField,
                formHireDateField);

        if (success) {
            masterData.setAll(employeesController.getEmployeesUtil().employees);
            clearForm();
        }
    }

    private void handleUpdate() {
        if (hiddenEmployeeIDField.getText().isEmpty()) {
            FXUtils.setButtonError(updateButton);
            updateButton.setTooltip(new Tooltip("No employee selected for update."));
            return;
        }
        boolean success = employeesController.updateEmployee(
                hiddenEmployeeIDField,
                formFirstNameField,
                formLastNameField,
                formPositionField,
                formSalaryField,
                formHireDateField);

        if (success) {
            masterData.setAll(employeesController.getEmployeesUtil().employees);
            clearForm();
        }
    }

    private void handleReport() {
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            com.example.components.CustomAlerts.showError("No employee selected. Please select an employee first.");
            return;
        }
        int employeeID = selectedEmployee.getEmployeeID();
        new ReportView("employees", employeeID);
    }

    private void clearForm() {
        hiddenEmployeeIDField.clear();
        formFirstNameField.clear();
        formLastNameField.clear();
        formPositionField.clear();
        formSalaryField.clear();
        formHireDateField.clear();

        FXUtils.resetFieldStyles(formFirstNameField);
        FXUtils.resetFieldStyles(formLastNameField);
        FXUtils.resetFieldStyles(formPositionField);
        FXUtils.resetFieldStyles(formSalaryField);
        FXUtils.resetFieldStyles(formHireDateField);

        formFirstNameField.setPromptText("First Name");
        formLastNameField.setPromptText("Last Name");
        formPositionField.setPromptText("Position");
        formSalaryField.setPromptText("Salary");
        formHireDateField.setPromptText("Hire Date (YYYY-MM-DD)");

        searchEmployeeIDField.clear();
        searchFirstNameField.clear();
        searchLastNameField.clear();
        searchPositionField.clear();
        searchSalaryField.clear();
        searchHireDateField.clear();

        searchEmployeeIDField.setPromptText("Employee ID");
        searchFirstNameField.setPromptText("First Name");
        searchLastNameField.setPromptText("Last Name");
        searchPositionField.setPromptText("Position");
        searchSalaryField.setPromptText("Salary");
        searchHireDateField.setPromptText("Hire Date (YYYY-MM-DD)");

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
