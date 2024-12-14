package com.example.ui;

import com.example.components.CustomHBox;

import javafx.scene.control.ScrollPane;

public class Overview extends ScrollPane{
    public Overview(String className, CustomHBox viewContainer) {
        getStyleClass().add(className);
        prefWidthProperty().bind(viewContainer.widthProperty().multiply(0.95));

    }
    
}
