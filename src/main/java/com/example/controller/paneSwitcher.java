package com.example.controller;

import java.util.List;

import com.example.components.CustomHBox;
import com.example.ui.CarsView;
import com.example.ui.CustomersView;
import com.example.ui.DashboardSidebar;
import com.example.ui.EmployeesView;
import com.example.ui.OrdersView;
import com.example.ui.PaymentsView;
import com.example.ui.ServicesView;
import com.example.ui.SettingsView;

public class paneSwitcher {

    public static void switchCars(CustomHBox viewContainer) {
        viewContainer.getChildren().clear();
        CarsView carsView = new CarsView(viewContainer);
        DashboardSidebar sideBar = new DashboardSidebar(viewContainer);
        List<CustomHBox> items = sideBar.getSidebarItems();
        items.forEach(item -> {
            item.getChildren().forEach(child -> child.getStyleClass().remove("active"));
        });
        sideBar.cars.getStyleClass().add("active");
        sideBar.cars.getChildren().forEach(child -> child.getStyleClass().add("active"));
        viewContainer.getChildren().addAll(sideBar, carsView);
    }

    public static void switchServices(CustomHBox viewContainer) {
        viewContainer.getChildren().clear();
        ServicesView servicesView = new ServicesView(viewContainer);
        DashboardSidebar sideBar = new DashboardSidebar(viewContainer);
        List<CustomHBox> items = sideBar.getSidebarItems();
        items.forEach(item -> {
            item.getChildren().forEach(child -> child.getStyleClass().remove("active"));
        });
        sideBar.services.getStyleClass().add("active");
        sideBar.services.getChildren().forEach(child -> child.getStyleClass().add("active"));
        viewContainer.getChildren().addAll(sideBar, servicesView);
    }

    public static void switchCustomers(CustomHBox viewContainer) {
        viewContainer.getChildren().clear();
        CustomersView customersView = new CustomersView(viewContainer);
        DashboardSidebar sideBar = new DashboardSidebar(viewContainer);
        List<CustomHBox> items = sideBar.getSidebarItems();
        items.forEach(item -> {
            item.getChildren().forEach(child -> child.getStyleClass().remove("active"));
        });
        sideBar.customers.getStyleClass().add("active");
        sideBar.customers.getChildren().forEach(child -> child.getStyleClass().add("active"));
        viewContainer.getChildren().addAll(sideBar, customersView);
    }

    public static void switchEmployees(CustomHBox viewContainer) {
        viewContainer.getChildren().clear();
        EmployeesView employeesView = new EmployeesView(viewContainer);
        DashboardSidebar sideBar = new DashboardSidebar(viewContainer);
        List<CustomHBox> items = sideBar.getSidebarItems();
        items.forEach(item -> {
            item.getChildren().forEach(child -> child.getStyleClass().remove("active"));
        });
        sideBar.employees.getStyleClass().add("active");
        sideBar.employees.getChildren().forEach(child -> child.getStyleClass().add("active"));
        viewContainer.getChildren().addAll(sideBar, employeesView);
    }

    public static void switchOrders(CustomHBox viewContainer) {
        viewContainer.getChildren().clear();
        OrdersView ordersView = new OrdersView(viewContainer);
        DashboardSidebar sideBar = new DashboardSidebar(viewContainer);
        List<CustomHBox> items = sideBar.getSidebarItems();
        items.forEach(item -> {
            item.getChildren().forEach(child -> child.getStyleClass().remove("active"));
        });
        sideBar.orders.getStyleClass().add("active");
        sideBar.orders.getChildren().forEach(child -> child.getStyleClass().add("active"));
        viewContainer.getChildren().addAll(sideBar, ordersView);
    }

    public static void switchPayments(CustomHBox viewContainer) {
        viewContainer.getChildren().clear();
        PaymentsView paymentsView = new PaymentsView(viewContainer);
        DashboardSidebar sideBar = new DashboardSidebar(viewContainer);
        List<CustomHBox> items = sideBar.getSidebarItems();
        items.forEach(item -> {
            item.getChildren().forEach(child -> child.getStyleClass().remove("active"));
        });
        sideBar.payment.getStyleClass().add("active");
        sideBar.payment.getChildren().forEach(child -> child.getStyleClass().add("active"));
        viewContainer.getChildren().addAll(sideBar, paymentsView);
    }
    public static void switchSettings(CustomHBox viewContainer) {
        viewContainer.getChildren().clear();
        SettingsView settingsView = new SettingsView(viewContainer);
        DashboardSidebar sideBar = new DashboardSidebar(viewContainer);
        List<CustomHBox> items = sideBar.getSidebarItems();
        items.forEach(item -> {
            item.getChildren().forEach(child -> child.getStyleClass().remove("active"));
        });
        sideBar.settings.getStyleClass().add("active");
        sideBar.settings.getChildren().forEach(child -> child.getStyleClass().add("active"));
        viewContainer.getChildren().addAll(sideBar, settingsView);
    }
}
