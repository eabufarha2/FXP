package com.example.controller;

import java.util.List;

import com.example.App;
import com.example.model.UserAccount;
import com.example.ui.DashboardView;
import com.example.ui.SignInView;
import com.example.ui.SignUpView;
import com.example.util.ReadUsers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SignInController {
    public void signUpClick() {
        App.stage.close();
        Stage stage = new SignUpView();
        App.stage = stage;
        App.stage.show();
    }

    public void signInClick() {
        ReadUsers users = new ReadUsers();
        List<UserAccount> userAccounts = users.userAccounts;
        for (UserAccount userAccount : userAccounts) {
            boolean isAuthenticated = false;
            if (userAccount.getUsername().equals(SignInView.userName.getText())
                    && userAccount.getPassword().equals(SignInView.passwordField.getText())) {
                App.stage.close();
                Stage stage = new DashboardView();
                App.stage = stage;
                App.stage.show();
                isAuthenticated = true;
                return;
            }
            if (!isAuthenticated) {
                SignInView.userName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
                SignInView.passwordField.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
                SignInView.errorText.setVisible(true);
            }
        }

    }

}
