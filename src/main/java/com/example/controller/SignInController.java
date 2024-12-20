package com.example.controller;

import java.util.List;
import com.example.App;
import com.example.components.CustomField;
import com.example.components.CustomPassword;
import com.example.components.CustomText;
import com.example.model.UserAccount;
import com.example.security.PasswordUtils;
import com.example.ui.DashboardView;
import com.example.ui.SignUpView;
import com.example.util.FXUtils;
import com.example.util.UsersUtil;

import javafx.stage.Stage;

public class SignInController {

    public static void signUpClick() {
        Switcher.switchScene(new SignUpView());
    }

    public static void signInClick(CustomField username, CustomPassword password, CustomText errorText) {
        FXUtils.resetFieldStyles(username);
        FXUtils.resetFieldStyles(password);
        boolean hasError = false;

        if (!FXUtils.checkNullField(username)) {
            hasError = true;
        }
        if (!FXUtils.checkNullField(password)) {
            hasError = true;
        }
        if (hasError) {
            return;
        }
        UsersUtil readUsers = new UsersUtil();
        List<UserAccount> userAccounts = readUsers.userAccounts;
        boolean isAuthenticated = false;
        for (UserAccount userAccount : userAccounts) {
            if (userAccount.getUsername().equals(username.getText())
                    && userAccount.getPassword().equals(PasswordUtils.hashPassword(password.getText()))) {
                UsersUtil.activeUser = new UserAccount(userAccount.getFirstName(), userAccount.getLastName(),
                        userAccount.getUsername(), userAccount.getPassword());
                isAuthenticated = true;
                App.stage.close();
                Stage stage = new DashboardView();
                App.stage = stage;
                App.stage.show();
                return;
            }
        }
        if (!isAuthenticated) {
            username.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            password.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            errorText.setVisible(true);
        }
    }

}
