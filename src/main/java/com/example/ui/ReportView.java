package com.example.ui;

import com.example.components.CustomButtom;
import com.example.controller.ReportController;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReportView extends Stage {
    private String entity;
    private int selectedID;
    private ComboBox<String> reportTypeCombo;
    private ComboBox<String> periodCombo;

    public ReportView(String entity, int selectedID) {
        this.entity = entity;
        this.selectedID = selectedID;
        setTitle("Generate Report");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(400, 300);

        reportTypeCombo = new ComboBox<>();
        periodCombo = new ComboBox<>();
        periodCombo.setVisible(false);

        loadReportsForEntity(entity);

        CustomButtom generateButton = new CustomButtom("Generate");
        generateButton.setOnAction(e -> {
            ReportController.generateReport(entity, selectedID, reportTypeCombo, periodCombo);
        });

        root.getChildren().addAll(reportTypeCombo);
        if (entity.equals("services")) {
            root.getChildren().add(periodCombo);
        }
        // For orders and payments, no extra period needed
        Scene scene = new Scene(root);
        root.getChildren().add(generateButton);

        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
        show();
    }

    private void loadReportsForEntity(String entity) {
        reportTypeCombo.getItems().clear();
        periodCombo.getItems().clear();
        periodCombo.setVisible(false);

        switch (entity) {
            case "cars":
                reportTypeCombo.getItems().add("Services done on this car");
                break;

            case "customers":
                reportTypeCombo.getItems().addAll(
                        "Services done for this customer",
                        "Payment methods and total paid by this customer");
                break;

            case "employees":
                reportTypeCombo.getItems().add("Sales report by this employee");
                break;

            case "services":
                reportTypeCombo.getItems().addAll(
                        "Revenue report by service (monthly or quarterly)",
                        "Services count done on each car");
                periodCombo.getItems().addAll("Monthly", "Quarterly");
                reportTypeCombo.setOnAction(e -> {
                    if ("Revenue report by service (monthly or quarterly)".equals(reportTypeCombo.getValue())) {
                        periodCombo.setVisible(true);
                    } else {
                        periodCombo.setVisible(false);
                    }
                });
                break;

            case "orders":
                reportTypeCombo.getItems().add("Monthly revenue from orders");
                break;

            case "payments":
                reportTypeCombo.getItems().add("Total amounts paid by payment method");
                break;
        }
    }
}
