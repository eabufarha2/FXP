package com.example.ui;

import org.kordamp.ikonli.fontawesome.FontAwesome;

import com.example.components.CustomButtom;
import com.example.components.CustomFont;
import com.example.components.CustomHBox;
import com.example.components.CustomText;
import com.example.components.CustomVBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Dashboard extends Stage {

    public Dashboard() {
        setMaximized(true);
        setTitle("CarMinder");

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 1820, 980);
        super.setScene(scene);

        borderPane.getStylesheets().add(getClass().getResource("/Styles/dashboard.css").toExternalForm());
        borderPane.getStyleClass().add("boarder_container");
        borderPane.setPadding(new Insets(35, 35, 35, 35));

        CustomHBox viewContainer = new CustomHBox("view_container", Pos.CENTER);
        borderPane.setCenter(viewContainer);

        CustomVBox leftVBox = new CustomVBox("sideBar", Pos.CENTER_LEFT);
        leftVBox.prefWidthProperty().bind(viewContainer.widthProperty().multiply(0.15));

        CustomText title = new CustomText("CarMinder", "title");
        CustomText menu = new CustomText("MENU", "menu");

        CustomHBox overview = new CustomHBox("overview", Pos.CENTER_LEFT);
        CustomFont homeIcon = new CustomFont(FontAwesome.TH, "overview_icon");
        CustomText overviewText = new CustomText("Overview", "overview_text");
        overview.getChildren().addAll(homeIcon, overviewText);

        CustomHBox cars = new CustomHBox("cars", Pos.CENTER_LEFT);
        CustomText carsText = new CustomText("Cars", "cars_text");
        CustomFont carIcon = new CustomFont(FontAwesome.CAR, "cars_icon");
        cars.getChildren().addAll(carIcon, carsText);

        CustomHBox customers = new CustomHBox("customers", Pos.CENTER_LEFT);
        CustomText customersText = new CustomText("Customers", "customers_text");
        CustomFont customerIcon = new CustomFont(FontAwesome.USER, "customers_icon");
        customers.getChildren().addAll(customerIcon, customersText);

        CustomHBox employees = new CustomHBox("employees", Pos.CENTER_LEFT);
        CustomText employeesText = new CustomText("Employees", "employees_text");
        CustomFont employeeIcon = new CustomFont(FontAwesome.USER, "employees_icon");
        employees.getChildren().addAll(employeeIcon, employeesText);

        CustomHBox orders = new CustomHBox("orders", Pos.CENTER_LEFT);
        CustomText ordersText = new CustomText("Orders", "orders_text");
        CustomFont ordersIcon = new CustomFont(FontAwesome.SHOPPING_CART, "orders_icon");
        orders.getChildren().addAll(ordersIcon, ordersText);

        CustomHBox payment = new CustomHBox("payment", Pos.CENTER_LEFT);
        CustomText paymentText = new CustomText("Payment", "payment_text");
        CustomFont paymentIcon = new CustomFont(FontAwesome.CREDIT_CARD, "payment_icon");
        payment.getChildren().addAll(paymentIcon, paymentText);

        CustomHBox services = new CustomHBox("services", Pos.CENTER_LEFT);
        CustomText servicesText = new CustomText("Services", "services_text");
        CustomFont servicesIcon = new CustomFont(FontAwesome.HANDSHAKE_O, "services_icon");
        services.getChildren().addAll(servicesIcon, servicesText);

        CustomVBox sectionsContainer = new CustomVBox("sections_container", Pos.CENTER_LEFT);
        CustomVBox tablesContainer = new CustomVBox("tables_container", Pos.CENTER_LEFT);
        tablesContainer.getChildren().addAll(overview, cars, customers, employees, orders, payment, services);
        sectionsContainer.getChildren().addAll(menu, tablesContainer);

        CustomVBox generalSection = new CustomVBox("general_section", Pos.CENTER_LEFT);
        CustomText generalText = new CustomText("GENERAL", "general_text");
        CustomHBox settings = new CustomHBox("settings", Pos.CENTER_LEFT);
        CustomText settingsText = new CustomText("Settings", "settings_text");
        settings.getChildren().addAll(settingsText);

        CustomButtom logoutButton = new CustomButtom("Logout", "logout_button");

        generalSection.getChildren().addAll(generalText, settings);

        leftVBox.getChildren().addAll(title, sectionsContainer, generalSection, logoutButton);

        CustomVBox rightVBox = new CustomVBox("content", Pos.CENTER);
        rightVBox.prefWidthProperty().bind(viewContainer.widthProperty().multiply(0.85));
        viewContainer.getChildren().addAll(leftVBox, rightVBox);

    }

}
