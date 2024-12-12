package com.example;

import com.example.ui.SignInView;
import com.example.ui.SignUpView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage stage; 
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage = new SignInView();
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
