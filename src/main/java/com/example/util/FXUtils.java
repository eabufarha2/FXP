package com.example.util;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class FXUtils {

    public static boolean checkNullField(TextField field) {
        if (field.getText().trim().isEmpty()) {
            setFieldError(field, "This field is required.");
            return false;
        }
        resetFieldStyles(field);
        return true;
    }

    public static boolean isValidEmail(TextField field) {
        if (!field.getText().trim().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            setFieldError(field, "Please enter a valid email address.");
            return false;
        }
        resetFieldStyles(field);
        return true;
    }

    public static boolean isValidPhone(TextField field) {
        if (!field.getText().trim().matches("^\\d{3}-\\d{4}$")) {
            setFieldError(field, "Please enter a valid phone number (e.g., 123-4567).");
            return false;
        }
        resetFieldStyles(field);
        return true;
    }

    public static boolean isValidDate(TextField field) {
        if (!field.getText().trim().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            setFieldError(field, "Please enter a valid date (e.g., YYYY-MM-DD).");
            return false;
        }
        resetFieldStyles(field);
        return true;
    }

    public static boolean checkAlphaField(TextField field) {
        if (!field.getText().trim().matches("^[a-zA-Z\\s]+$")) {
            setFieldError(field);
            field.setTooltip(new Tooltip("This field must contain only letters and spaces."));
            return false;
        }
        resetFieldStyles(field);
        return true;
    }

    public static boolean checkNumericField(TextField field) {
        if (!field.getText().trim().matches("^[0-9]+$")) {
            setFieldError(field);
            field.setTooltip(new Tooltip("This field must contain only numbers."));
            return false;
        }
        resetFieldStyles(field);
        return true;
    }

    public static boolean checkYearField(TextField field) {
        if (!field.getText().trim().matches("^(19|20)\\d{2}$")) {
            setFieldError(field);
            field.setTooltip(new Tooltip("Please enter a valid year (e.g., 1900 - 2099)."));
            return false;
        }
        resetFieldStyles(field);
        return true;
    }

    public static boolean checkIntOrDoubleField(TextField field) {
        if (!field.getText().trim().matches("^-?\\d+(\\.\\d+)?$")) {
            setFieldError(field);
            field.setTooltip(new Tooltip("Please enter a valid number (integer or decimal)."));
            return false;
        }
        resetFieldStyles(field);
        return true;
    }

    public static boolean checkVINField(TextField field) {
        if (!field.getText().trim().matches("^[A-HJ-NPR-Z0-9]{17}$")) {
            setFieldError(field);
            field.setTooltip(new Tooltip("Please enter a valid 17-character VIN number."));
            return false;
        }
        resetFieldStyles(field);
        return true;
    }

    public static void setFieldError(TextField field, String message) {
        field.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        field.setTooltip(new Tooltip(message));
    }

    public static void setFieldError(TextField field) {
        field.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
    }

    public static void setButtonError(Button button) {
        button.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
    }

    public static void resetButtonStyles(Button button) {
        button.setStyle("");
    }

    public static void resetFieldStyles(TextField field) {
        field.setStyle("");
        field.setTooltip(null);
    }

    public static void resetFieldStyles(TextField... fields) {
        for (TextField field : fields) {
            resetFieldStyles(field);
        }
    }
}
