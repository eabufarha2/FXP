package com.example.components;

import javafx.scene.text.Text;

public class CustomText extends Text {
    public CustomText(String text, String className) {
        super(text);
        getStyleClass().add(className);
    }

    public CustomText(String text) {
        super(text);
    }
    
    public CustomText() {
        super();
    }
    
}
