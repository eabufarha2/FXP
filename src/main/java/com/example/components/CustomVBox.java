package com.example.components;

import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class CustomVBox extends VBox {
    public CustomVBox(String className, Pos alignment) {
        super();
        getStyleClass().add(className);
        setAlignment(alignment);
    }

    public CustomVBox(String className) {
        super();
        getStyleClass().add(className);
    }

    public CustomVBox() {
        super();
    }
}
