package com.example.controller;

import com.example.components.CustomField;
import com.example.components.CustomPassword;
import com.example.security.ExistingUser;
import com.example.ui.SignInView;
import com.example.util.FXUtils;
import com.example.util.UsersUtil;

public class SignUpController {

    public static void loginClick() {
        Switcher.switchScene(new SignInView());
    }

    public static void createAccountButton(CustomField firstNameField, CustomField lastNameField,
            CustomField usernameField, CustomPassword passwordField) {
        FXUtils.resetFieldStyles(firstNameField);
        FXUtils.resetFieldStyles(lastNameField);
        FXUtils.resetFieldStyles(usernameField);
        FXUtils.resetFieldStyles(passwordField);

        boolean hasError = false;

        if (!FXUtils.checkNullField(lastNameField)) {
            hasError = true;
        }
        if (!FXUtils.checkNullField(firstNameField)) {
            hasError = true;
        }
        if (!FXUtils.checkNullField(usernameField)) {
            hasError = true;
        }
        if (!FXUtils.checkNullField(passwordField)) {
            hasError = true;
        }
        if (!FXUtils.checkAlphaField(firstNameField)) {
            hasError = true;
        }
        if (!FXUtils.checkAlphaField(lastNameField)) {
            hasError = true;
        }
        if (hasError) {
            return;
        }

        if (ExistingUser.checkExistingUser(usernameField)) {
            FXUtils.setFieldError(usernameField, "Username is already taken.");
            return;
        }

        if (UsersUtil.insert(firstNameField, lastNameField, usernameField, passwordField)) {
            loginClick();
        }
    }

}
