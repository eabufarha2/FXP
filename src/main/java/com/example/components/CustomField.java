package com.example.components;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CustomField extends TextField {
    public CustomField(String promptText) {
        super();
        setPromptText(promptText);
        setFocusTraversable(false);
        HBox.setHgrow(this, Priority.ALWAYS);
    }

    public CustomField() {
        super();
        setFocusTraversable(false);
        HBox.setHgrow(this, Priority.ALWAYS);
    }
}
