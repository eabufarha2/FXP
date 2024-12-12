package com.example.controller;

import com.example.App;
import com.example.ui.SignInView;
import com.example.ui.SignUpView;

import javafx.stage.Stage;

public class SignInController {
    public void signUpClick() {
        App.stage.close();
        Stage stage = new SignUpView();
        App.stage = stage;
        App.stage.show();
    }
    
}
