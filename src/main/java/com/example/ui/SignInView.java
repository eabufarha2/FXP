package com.example.ui;

import com.example.components.CustomButtom;
import com.example.components.CustomField;
import com.example.components.CustomHBox;
import com.example.components.CustomPassword;
import com.example.components.CustomText;
import com.example.components.CustomVBox;
import com.example.components.LeftSideImage;
import com.example.controller.SignInController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SignInView extends Stage {
    public static CustomField userName;
    public static CustomPassword passwordField;
    public static CustomText errorText;
    private static SignInController signInController = new SignInController();

    public SignInView() {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 1820, 980);
        super.setScene(scene);
        borderPane.getStyleClass().add("boarder_container");
        borderPane.setPadding(new Insets(35, 35, 35, 35));

        CustomHBox centerContainer = new CustomHBox("vb_conatiner");
        borderPane.setCenter(centerContainer);

        // Left side VBox with image
        CustomVBox leftSide = new LeftSideImage("left_side", Pos.CENTER);
        leftSide.prefWidthProperty().bind(centerContainer.widthProperty().divide(2));

        // Right side VBox with form
        CustomVBox rightSide = createRightSide();
        rightSide.prefWidthProperty().bind(centerContainer.widthProperty().divide(2));

        centerContainer.getChildren().addAll(leftSide, rightSide);
        borderPane.getStylesheets().add(getClass().getResource("/Styles/SignIn.css").toExternalForm());
        setMaximized(true);
    }

    private CustomVBox createRightSide() {
        CustomVBox rightSide = new CustomVBox("right_side", Pos.CENTER_LEFT);

        CustomVBox form = new CustomVBox("form", Pos.CENTER_LEFT);

        CustomText loginTxt = new CustomText("Agent Login", "loginTxt");

        CustomVBox fieldsContainer = new CustomVBox("fields");
        userName = new CustomField("Username");
        passwordField = new CustomPassword("Password");
        errorText = new CustomText("Invalid username or password", "errorText");
        errorText.setVisible(false);
        fieldsContainer.getChildren().addAll(userName, passwordField, errorText);

        CustomButtom createAccountButton = new CustomButtom("Sign In");
        createAccountButton.setOnMouseClicked(e -> signInController.signInClick());

        CustomHBox signUp = new CustomHBox("signUpContainer");
        CustomText signUpText = new CustomText("Don't have an account?", "texts");
        CustomText signUpLink = new CustomText("Sign Up", "signup");

        signUpLink.setOnMouseClicked(e -> signInController.signUpClick());
        signUp.getChildren().addAll(signUpText, signUpLink);
        form.getChildren().addAll(loginTxt, fieldsContainer, createAccountButton, signUp);
        rightSide.getChildren().add(form);
        return rightSide;
    }
}
