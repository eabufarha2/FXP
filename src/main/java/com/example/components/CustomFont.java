package com.example.components;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

public class CustomFont extends FontIcon {

    public CustomFont(FontAwesome icon, String className) {
        super(icon);
        getStyleClass().add(className);
        getStyleClass().add("Fonts");

    }

}
