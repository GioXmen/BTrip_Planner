package com.btplanner.btripex.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private Long id;
    private String username;
    private String password;

    LoggedInUserView(String username, String password, Long id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    Long getId() {
        return id;
    }
}
