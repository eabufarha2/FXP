package com.example.components;

import javafx.scene.control.PasswordField;

public class CustomPassword extends PasswordField {
    public CustomPassword(String promptText) {
        super();
        setPromptText(promptText);
        setFocusTraversable(false);
    }

    public CustomPassword() {
        super();
        setFocusTraversable(false);
    }
}
