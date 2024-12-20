package com.example.util;

import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class FXToast {

    public static void showToast(Window window, String message) {
        // Create a popup
        Popup popup = new Popup();

        // Create a label for the toast
        Label label = new Label(message);
        label.setStyle("-fx-background-color: #323232; -fx-background-radius: 5; -fx-padding: 10 20 10 20;");
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Arial", 14));

        StackPane root = new StackPane(label);
        root.setStyle("-fx-background-color: transparent; -fx-background-radius: 5;");
        popup.getContent().add(root);

        double x = window.getX() + window.getWidth() / 2 - label.prefWidth(-1) / 2;
        double y = window.getY() + window.getHeight() - 100;
        popup.setX(x);
        popup.setY(y);

        popup.show(window);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), root);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> popup.hide());

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> fadeOut.play());
        delay.play();
    }
}
