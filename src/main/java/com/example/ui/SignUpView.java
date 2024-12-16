package com.example.ui;

import javafx.scene.layout.BorderPane;

import com.example.App;
import com.example.components.CustomButtom;
import com.example.components.CustomField;
import com.example.components.CustomHBox;
import com.example.components.CustomPassword;
import com.example.components.CustomText;
import com.example.components.CustomVBox;
import com.example.components.LeftSideImage;
import com.example.controller.SignUpController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class SignUpView extends Stage {

    public SignUpView() {
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
        borderPane.getStylesheets().add(getClass().getResource("/Styles/sign_up.css").toExternalForm());
        setMaximized(true);

    }

    private CustomVBox createRightSide() {
        CustomVBox rightSide = new CustomVBox("right_side", Pos.CENTER_LEFT);
        CustomVBox form = new CustomVBox("form", Pos.CENTER_LEFT);
        CustomVBox textContainer = new CustomVBox("text_container");
        CustomText createAccTxt = new CustomText("Create an account", "CreateAccTxt");
        CustomHBox signInContainer = new CustomHBox("signInContainer");
        CustomText haveAccountTxt = new CustomText("Already have an account?", "texts");
        CustomText signInLink = new CustomText("Log in", "signin");

        signInLink.setOnMouseClicked(e -> SignUpController.loginClick());

        signInContainer.getChildren().addAll(haveAccountTxt, signInLink);
        textContainer.getChildren().addAll(createAccTxt, signInContainer);

        CustomVBox fieldsContainer = new CustomVBox("fields");
        CustomHBox nameContainer = new CustomHBox("name");
        CustomField firstNameField = new CustomField("First name");
        CustomField lastNameField = new CustomField("Last name");
        nameContainer.getChildren().addAll(firstNameField, lastNameField);

        CustomField userName = new CustomField("Username");
        CustomPassword passwordField = new CustomPassword("Password");
        fieldsContainer.getChildren().addAll(nameContainer, userName, passwordField);

        CustomButtom createAccountButton = new CustomButtom("Create Account");
        createAccountButton.setOnMouseClicked(e -> {
            SignUpController controller = new SignUpController();
            controller.createAccountButton(firstNameField,
                    lastNameField,
                    userName,
                    passwordField);
        });
        form.getChildren().addAll(textContainer, fieldsContainer, createAccountButton);
        rightSide.getChildren().add(form);

        return rightSide;
    }
}