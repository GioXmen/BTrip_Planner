package com.btplanner.btripex.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
public class LoggedInUserView {
    private String username;
    private String password;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }
}
