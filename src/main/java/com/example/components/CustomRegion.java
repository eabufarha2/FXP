package com.example.components;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class CustomRegion extends Region {
    public CustomRegion() {
        HBox.setHgrow(this, Priority.ALWAYS);
    }
}
