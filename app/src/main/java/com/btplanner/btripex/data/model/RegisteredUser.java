package com.btplanner.btripex.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Data class that captures user information for registered users retrieved from RegisterRepository
 */
public class RegisteredUser {

    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public RegisteredUser(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
