package com.example.components;

import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

public class CustomHBox extends HBox {
    public CustomHBox(String className, Pos alignment) {
        super();
        getStyleClass().add(className);
        setAlignment(alignment);
    }

    public CustomHBox(String className) {
        super();
        getStyleClass().add(className);
    }

    public CustomHBox() {
        super();
    }
}
