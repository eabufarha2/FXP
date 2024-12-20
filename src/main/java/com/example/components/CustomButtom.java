package com.example.components;

import javafx.scene.Cursor;
import javafx.scene.control.Button;

public class CustomButtom extends Button {
    public CustomButtom(String text) {
        super(text);
        setCursor(Cursor.HAND);
    }

    public CustomButtom(String text, String className) {
        super(text);
        getStyleClass().add(className);
        setCursor(Cursor.HAND);
    }
}
