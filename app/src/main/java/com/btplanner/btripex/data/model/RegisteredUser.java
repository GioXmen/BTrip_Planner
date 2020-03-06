package com.btplanner.btripex.data.model;

/**
 * Data class that captures user information for registered users retrieved from RegisterRepository
 */
public class RegisteredUser {

    private String userId;
    private String displayName;

    public RegisteredUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
