package com.example.components;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LeftSideImage extends CustomVBox {

    public LeftSideImage(String styleClass, Pos alignment) {
        super(styleClass, alignment);

        ImageView imageView = new ImageView(
                new Image(getClass().getResource("/Images/download.png").toExternalForm()));

        imageView.setFitHeight(720);
        imageView.setFitWidth(720);
        imageView.getStyleClass().add("image");
        getChildren().add(imageView);
    }
}
