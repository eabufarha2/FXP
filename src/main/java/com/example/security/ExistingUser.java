package com.example.security;

import com.example.components.CustomField;
import com.example.model.UserAccount;
import com.example.util.Users;

public class ExistingUser {
    public static boolean checkExistingUser(CustomField usernameField) {
        String username = usernameField.getText().trim();
        Users readUsers = new Users();
        for (UserAccount user : readUsers.userAccounts) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
