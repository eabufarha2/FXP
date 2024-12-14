package com.example.ui;

import org.kordamp.ikonli.fontawesome.FontAwesome;

import com.example.components.CustomBorderPane;
import com.example.components.CustomButtom;
import com.example.components.CustomFont;
import com.example.components.CustomHBox;
import com.example.components.CustomText;
import com.example.components.CustomVBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DashboardView extends Stage {
    static ScrollPane contentPane;
    static CustomBorderPane borderPane;

    public DashboardView() {
        setMaximized(true);
        setTitle("CarMinder");

        borderPane = new CustomBorderPane("boarder_container", "/Styles/dashboard.css");

        Scene scene = new Scene(borderPane, 1820, 980);
        super.setScene(scene);

        CustomHBox viewContainer = new CustomHBox("view_container", Pos.CENTER);
        borderPane.setCenter(viewContainer);

        CustomVBox sideBar = new DashboardSidebar("sideBar", Pos.CENTER_LEFT, viewContainer);

        contentPane = new Overview("content", viewContainer);

        viewContainer.getChildren().addAll(sideBar, contentPane);

    }

}
