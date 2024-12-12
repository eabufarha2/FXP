package com.example.controller;

import java.util.List;

import com.example.App;
import com.example.model.UserAccount;
import com.example.ui.SignUpView;
import com.example.util.ReadUsers;

import javafx.stage.Stage;

public class SignInController {
    public void signUpClick() {
        App.stage.close();
        Stage stage = new SignUpView();
        App.stage = stage;
        App.stage.show();
    }
    public void signInClick(){
        ReadUsers users = new ReadUsers();
        List<UserAccount> userAccounts = users.userAccounts;
        
        
    }
    
}
