package com.example.ui;

import java.util.Arrays;
import java.util.List;

import org.kordamp.ikonli.fontawesome.FontAwesome;

import com.example.components.CustomButtom;
import com.example.components.CustomFont;
import com.example.components.CustomHBox;
import com.example.components.CustomText;
import com.example.components.CustomVBox;
import com.example.controller.DashboardSidebarController;
import com.example.controller.paneSwitcher;

import javafx.geometry.Pos;

public class DashboardSidebar extends CustomVBox {
    public CustomHBox cars;
    public CustomHBox customers;
    public CustomHBox employees;
    public CustomHBox orders;
    public CustomHBox payment;
    public CustomHBox services;
    public CustomHBox settings;

    public DashboardSidebar(CustomHBox viewContainer) {
        super("sideBar", Pos.CENTER_LEFT);
        getStylesheets().add(getClass().getResource("/Styles/dashboardSidebar.css").toExternalForm());
        CustomText title = new CustomText("CarMinder", "title");
        CustomText menu = new CustomText("MENU", "menu");   

        cars = new CustomHBox("cars", Pos.CENTER_LEFT);
        cars.getStyleClass().add("active");
        CustomText carsText = new CustomText("Cars", "cars_text");
        carsText.getStyleClass().add("active");
        CustomFont carIcon = new CustomFont(FontAwesome.CAR, "cars_icon");
        carIcon.getStyleClass().add("active");
        cars.getChildren().addAll(carIcon, carsText);
        cars.setOnMouseClicked(e -> paneSwitcher.switchCars(viewContainer));

        customers = new CustomHBox("customers", Pos.CENTER_LEFT);
        CustomText customersText = new CustomText("Customers", "customers_text");
        CustomFont customerIcon = new CustomFont(FontAwesome.USER, "customers_icon");
        customers.getChildren().addAll(customerIcon, customersText);
        customers.setOnMouseClicked(e -> paneSwitcher.switchCustomers(viewContainer));

        employees = new CustomHBox("employees", Pos.CENTER_LEFT);
        CustomText employeesText = new CustomText("Employees", "employees_text");
        CustomFont employeeIcon = new CustomFont(FontAwesome.USER, "employees_icon");
        employees.getChildren().addAll(employeeIcon, employeesText);
        employees.setOnMouseClicked(e -> paneSwitcher.switchEmployees(viewContainer));

        orders = new CustomHBox("orders", Pos.CENTER_LEFT);
        CustomText ordersText = new CustomText("Orders", "orders_text");
        CustomFont ordersIcon = new CustomFont(FontAwesome.SHOPPING_CART, "orders_icon");
        orders.getChildren().addAll(ordersIcon, ordersText);
        orders.setOnMouseClicked(e -> paneSwitcher.switchOrders(viewContainer));

        payment = new CustomHBox("payment", Pos.CENTER_LEFT);
        CustomText paymentText = new CustomText("Payment", "payment_text");
        CustomFont paymentIcon = new CustomFont(FontAwesome.CREDIT_CARD, "payment_icon");
        payment.getChildren().addAll(paymentIcon, paymentText);
        payment.setOnMouseClicked(e -> paneSwitcher.switchPayments(viewContainer));

        services = new CustomHBox("services", Pos.CENTER_LEFT);
        CustomText servicesText = new CustomText("Services", "services_text");
        CustomFont servicesIcon = new CustomFont(FontAwesome.HANDSHAKE_O, "services_icon");
        services.getChildren().addAll(servicesIcon, servicesText);
        services.setOnMouseClicked(e -> paneSwitcher.switchServices(viewContainer));

        CustomVBox sectionsContainer = new CustomVBox("sections_container", Pos.CENTER_LEFT);
        CustomVBox tablesContainer = new CustomVBox("tables_container", Pos.CENTER_LEFT);
        tablesContainer.getChildren().addAll(cars, customers, employees, orders, payment, services);
        sectionsContainer.getChildren().addAll(menu, tablesContainer);

        CustomVBox generalSection = new CustomVBox("general_section", Pos.CENTER_LEFT);
        CustomText generalText = new CustomText("GENERAL", "general_text");
        settings = new CustomHBox("settings", Pos.CENTER_LEFT);
        CustomText settingsText = new CustomText("Settings", "settings_text");
        CustomFont settingsIcon = new CustomFont(FontAwesome.COG, "settings_icon");
        settings.getChildren().addAll(settingsIcon, settingsText);
        settings.setOnMouseClicked(e -> paneSwitcher.switchSettings(viewContainer));

        CustomButtom logoutButton = new CustomButtom("Logout", "logout_button");
        logoutButton.setOnMouseClicked(e -> DashboardSidebarController.logoutClick());

        generalSection.getChildren().addAll(generalText, settings);

        prefWidthProperty().bind(viewContainer.widthProperty().multiply(0.15));
        getChildren().addAll(title, sectionsContainer, generalSection, logoutButton);
    }

    public List<CustomHBox> getSidebarItems() {
        return Arrays.asList(cars, customers, employees, orders, payment, services);
    }
}
