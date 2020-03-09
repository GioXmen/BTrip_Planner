package com.btplanner.btripex.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

/**
 * Data class that captures user information for registered users retrieved from RegisterRepository
 */
public class RegisteredUser {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public RegisteredUser(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public RegisteredUser(String username, String password) {
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

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
