package com.example.util;

import com.example.components.CustomField;
import com.example.components.CustomText;

import javafx.scene.control.TextField;

public class FXUtils {
    public static boolean checkNullField(TextField field) {
        if (field.getText().trim().isEmpty()) {
            setFieldError(field, "This field is required.");
            return true;
        }
        resetFieldStyles(field);
        return false;
    }

    public static void setFieldError(TextField field, String message) {
        field.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        field.setPromptText(message);
    }

    public static void resetFieldStyles(TextField field) {
        field.setStyle("");
        field.setPromptText("");
    }
}
