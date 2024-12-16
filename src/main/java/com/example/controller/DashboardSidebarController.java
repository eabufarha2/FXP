package com.example.controller;

import com.example.ui.SignInView;

public class DashboardSidebarController {
    public static void logoutClick() {
        Switcher.switchScene(new SignInView());
        
    }
}
