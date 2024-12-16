package com.example.controller;

import com.example.App;

import javafx.stage.Stage;

public class Switcher {

    public static void switchScene(Stage nexStage) {
        App.stage.close();
        App.stage = nexStage;
        App.stage.show();
    }

}
