package com.example;

import com.example.ui.Dashboard;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage stage; 
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage = new Dashboard();
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
