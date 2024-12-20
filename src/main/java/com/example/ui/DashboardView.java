package com.example.ui;

import org.kordamp.ikonli.fontawesome.FontAwesome;

import com.example.components.CustomBorderPane;
import com.example.components.CustomHBox;
import com.example.components.CustomVBox;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class DashboardView extends Stage {
    public static ScrollPane contentPane;
    static CustomBorderPane borderPane;
    static CustomHBox viewContainer;

    public DashboardView() {
        setMaximized(true);
        setTitle("CarMinder");

        borderPane = new CustomBorderPane("boarder_container", "/Styles/dashboard.css");

        Scene scene = new Scene(borderPane, 1820, 980);
        super.setScene(scene);

        viewContainer = new CustomHBox("view_container", Pos.CENTER);
        borderPane.setCenter(viewContainer);

        CustomVBox sideBar = new DashboardSidebar(viewContainer);

        contentPane = new CarsView(viewContainer);

        viewContainer.getChildren().addAll(sideBar, contentPane);
    }
}
