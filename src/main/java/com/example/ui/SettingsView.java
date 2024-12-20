package com.example.ui;

import com.example.components.CustomAlerts;
import com.example.components.CustomBorderPane;
import com.example.components.CustomButtom;
import com.example.components.CustomField;
import com.example.components.CustomHBox;
import com.example.components.CustomPassword;
import com.example.components.CustomVBox;
import com.example.components.CustomText;
import com.example.security.PasswordUtils;
import com.example.util.FXUtils;
import com.example.util.UsersUtil;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * SettingsView allows the user to edit their profile, similar to other views.
 * It extends ScrollPane and uses a CustomBorderPane inside, following the
 * pattern
 * used by other views like CarsView, ServicesView, etc.
 */
public class SettingsView extends javafx.scene.control.ScrollPane {
    private CustomField firstNameField;
    private CustomField lastNameField;
    private CustomPassword currentPasswordField;
    private CustomPassword newPasswordField;
    private CustomPassword confirmPasswordField;

    private CustomButtom saveButton;

    public SettingsView(CustomHBox viewContainer) {
        getStyleClass().add("content");
        prefWidthProperty().bind(viewContainer.widthProperty().multiply(0.95));
        getStylesheets().add(getClass().getResource("/Styles/cars.css").toExternalForm());

        // Create a CustomBorderPane similar to other views
        CustomBorderPane borderPane = new CustomBorderPane();
        borderPane.getStyleClass().add("boarderPane");
        borderPane.prefWidthProperty().bind(this.widthProperty().subtract(40));
        borderPane.prefHeightProperty().bind(this.heightProperty().subtract(40));

        CustomVBox container = new CustomVBox("container", Pos.CENTER);
        container.setSpacing(15);

        // Fetch current user info
        String fName = UsersUtil.activeUser.getFirstName();
        String lName = UsersUtil.activeUser.getLastName();

        Label heading = new Label("Edit Your Profile");
        heading.getStyleClass().add("heading");

        firstNameField = new CustomField("First Name");
        firstNameField.setText(fName);

        lastNameField = new CustomField("Last Name");
        lastNameField.setText(lName);

        currentPasswordField = new CustomPassword("Current Password");
        newPasswordField = new CustomPassword("New Password");
        confirmPasswordField = new CustomPassword("Confirm New Password");

        saveButton = new CustomButtom("Save");
        saveButton.setOnAction(e -> handleSave());

        VBox form = new VBox(10, heading, firstNameField, lastNameField,
                currentPasswordField, newPasswordField, confirmPasswordField, saveButton);
        form.setAlignment(Pos.CENTER);
        form.setMaxWidth(300);
        form.setStyle("-fx-padding:20;");

        container.getChildren().add(form);
        borderPane.setCenter(container);

        setContent(borderPane);
    }

    private void handleSave() {
        FXUtils.resetFieldStyles(firstNameField, lastNameField, currentPasswordField, newPasswordField,
                confirmPasswordField);

        boolean valid = true;
        // Validate fields
        if (!FXUtils.checkNullField(firstNameField))
            valid = false;
        if (!FXUtils.checkAlphaField(firstNameField))
            valid = false;

        if (!FXUtils.checkNullField(lastNameField))
            valid = false;
        if (!FXUtils.checkAlphaField(lastNameField))
            valid = false;

        if (!FXUtils.checkNullField(currentPasswordField))
            valid = false;

        if (!valid) {
            return; // basic validation failed
        }

        // Verify current password
        String currentPasswordHash = PasswordUtils.hashPassword(currentPasswordField.getText().trim());
        if (!currentPasswordHash.equals(UsersUtil.getPassword())) {
            FXUtils.setFieldError(currentPasswordField, "Current password is incorrect.");
            return;
        }

        // If new password fields are not empty, validate them
        String newPass = newPasswordField.getText().trim();
        String confirmPass = confirmPasswordField.getText().trim();
        String finalPasswordHash;
        if (!newPass.isEmpty() || !confirmPass.isEmpty()) {
            // Both must be filled and match
            if (!FXUtils.checkNullField(newPasswordField))
                return;
            if (!FXUtils.checkNullField(confirmPasswordField))
                return;

            if (!newPass.equals(confirmPass)) {
                FXUtils.setFieldError(confirmPasswordField, "Passwords do not match.");
                return;
            }
            finalPasswordHash = PasswordUtils.hashPassword(newPass);
        } else {
            // No password change
            finalPasswordHash = UsersUtil.getPassword(); // keep old password
        }

        // Attempt update
        boolean updated = UsersUtil.updateActiveUser(
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                finalPasswordHash);

        if (updated) {
            CustomAlerts.showInformation("Profile updated successfully!");
        } else {
            CustomAlerts.showError("Failed to update profile. Please try again.");
        }
    }
}
