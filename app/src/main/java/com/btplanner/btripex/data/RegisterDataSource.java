package com.btplanner.btripex.data;

import com.btplanner.btripex.data.model.RegisteredUser;

import java.io.IOException;

public class RegisterDataSource {

    public Result<RegisteredUser> register(String username, String password) {

        try {
            // TODO: handle registration
            RegisteredUser fakeUser =
                    new RegisteredUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
