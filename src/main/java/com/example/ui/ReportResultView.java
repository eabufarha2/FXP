package com.example.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.print.PrinterJob;
import javafx.stage.FileChooser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportResultView extends Stage {

    private final TableView<ObservableList<String>> tableView;
    private final ObservableList<ObservableList<String>> fullData;
    private ObservableList<ObservableList<String>> filteredData;
    private final TextField searchField;
    private final Pagination pagination;

    private static final int ROWS_PER_PAGE = 50; // adjust as needed
    private final BorderPane root; // store root here

    public ReportResultView(List<String[]> tableData) {
        setTitle("Report Results");

        root = new BorderPane();
        root.setPadding(new Insets(15));

        // Extract headers and data
        String[] headers = tableData.get(0);
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (int r = 1; r < tableData.size(); r++) {
            String[] rowData = tableData.get(r);
            data.add(FXCollections.observableArrayList(rowData));
        }

        this.fullData = data;
        this.filteredData = FXCollections.observableArrayList(fullData);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(false);

        // Create columns
        for (int i = 0; i < headers.length; i++) {
            final int colIndex = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(headers[i]);
            col.setCellValueFactory(param -> {
                ObservableList<String> row = param.getValue();
                if (colIndex < row.size()) {
                    return new SimpleStringProperty(row.get(colIndex));
                } else {
                    return new SimpleStringProperty("");
                }
            });
            tableView.getColumns().add(col);
        }

        // Search Field to filter data
        searchField = new TextField();
        searchField.setPromptText("Search in report...");
        searchField.setOnKeyReleased(this::onSearchKeyReleased);

        // Buttons (Export, Print)
        Button exportBtn = new Button("Export to CSV");
        exportBtn.setOnAction(e -> exportToCSV());

        Button printBtn = new Button("Print");
        printBtn.setOnAction(e -> printTable());

        HBox toolbar = new HBox(10, searchField, exportBtn, printBtn);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        pagination = new Pagination();
        pagination.setPageFactory(this::createPage);
        // We'll set the initial content after scene initialization

        VBox topContainer = new VBox(10, toolbar);
        root.setTop(topContainer);
        root.setCenter(pagination); // set pagination now

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);

        // Now that the scene is set, we can safely call updatePagination()
        updatePagination();

        initModality(Modality.APPLICATION_MODAL);
        show();
    }

    private void onSearchKeyReleased(KeyEvent event) {
        String text = searchField.getText().toLowerCase().trim();
        if (text.isEmpty()) {
            filteredData.setAll(fullData);
        } else {
            filteredData.setAll(fullData.filtered(row -> {
                for (String cell : row) {
                    if (cell.toLowerCase().contains(text)) {
                        return true;
                    }
                }
                return false;
            }));
        }
        updatePagination();
    }

    private void exportToCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        var file = fileChooser.showSaveDialog(this);
        if (file != null) {
            try (FileWriter fw = new FileWriter(file)) {
                // Write headers
                for (int i = 0; i < tableView.getColumns().size(); i++) {
                    fw.append(tableView.getColumns().get(i).getText());
                    if (i < tableView.getColumns().size() - 1)
                        fw.append(",");
                }
                fw.append("\n");

                // Write data (filteredData)
                for (ObservableList<String> row : filteredData) {
                    for (int i = 0; i < row.size(); i++) {
                        fw.append(row.get(i));
                        if (i < row.size() - 1)
                            fw.append(",");
                    }
                    fw.append("\n");
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "CSV exported successfully!", ButtonType.OK);
                alert.showAndWait();

            } catch (IOException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to export CSV: " + ex.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    private void printTable() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean proceed = job.showPrintDialog(this);
            if (proceed) {
                boolean printed = job.printPage(tableView);
                if (printed) {
                    job.endJob();
                }
            }
        }
    }

    private void updatePagination() {
        int totalRows = filteredData.size();
        int pageCount = (int) Math.ceil((double) totalRows / ROWS_PER_PAGE);
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);
        createPage(0); // refresh page
    }

    private TableView<ObservableList<String>> createPage(int pageIndex) {
        int start = pageIndex * ROWS_PER_PAGE;
        int end = Math.min(start + ROWS_PER_PAGE, filteredData.size());

        TableView<ObservableList<String>> tempTable = new TableView<>();
        tempTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Copy columns structure
        for (TableColumn<ObservableList<String>, ?> col : tableView.getColumns()) {
            TableColumn<ObservableList<String>, String> newCol = new TableColumn<>(col.getText());
            newCol.setCellValueFactory(((TableColumn<ObservableList<String>, String>) col).getCellValueFactory());
            tempTable.getColumns().add(newCol);
        }

        if (start < end) {
            tempTable.setItems(FXCollections.observableArrayList(filteredData.subList(start, end)));
        } else {
            tempTable.setItems(FXCollections.observableArrayList());
        }

        // Instead of getting scene's root, use our stored `root`.
        // We replace center content with the new paged table.
        root.setCenter(tempTable);

        return tempTable;
    }
}
