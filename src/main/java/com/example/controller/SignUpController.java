package com.example.controller;

import com.example.App;
import com.example.ui.SignInView;

import javafx.stage.Stage;

public class SignUpController {
    public void logInClick() {
        App.stage.close();
        Stage stage = new SignInView();
        App.stage = stage;
        App.stage.show();
    }

    
}
