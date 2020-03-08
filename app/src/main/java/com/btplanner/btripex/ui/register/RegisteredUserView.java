package com.btplanner.btripex.ui.register;

/**
 * Class exposing authenticated user details to the UI.
 */

class RegisteredUserView {
    private String username;
    private String password;
    //... other data fields that may be accessible to the UI

    RegisteredUserView(String username, String password) {
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
