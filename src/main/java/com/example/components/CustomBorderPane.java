package com.example.components;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

public class CustomBorderPane extends BorderPane {
    public CustomBorderPane(String className, String styleSheet) {
        getStyleClass().add(className);
        setPadding(new Insets(35, 35, 35, 35));
        getStylesheets().add(getClass().getResource(styleSheet).toExternalForm());
    }
    
}
