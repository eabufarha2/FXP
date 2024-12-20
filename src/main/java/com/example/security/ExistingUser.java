package com.example.security;

import com.example.components.CustomField;
import com.example.model.UserAccount;
import com.example.util.UsersUtil;

public class ExistingUser {
    public static boolean checkExistingUser(CustomField usernameField) {
        String username = usernameField.getText().trim();
        UsersUtil readUsers = new UsersUtil();
        for (UserAccount user : readUsers.userAccounts) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
