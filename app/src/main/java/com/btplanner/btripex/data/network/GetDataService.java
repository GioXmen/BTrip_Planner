package com.btplanner.btripex.data.network;

import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.RegisteredUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/user/login")
    Call<LoggedInUser> login(@Query("username") String username, @Query("password") String password);

    @GET("/user/registration")
    Call<RegisteredUser> register(@Query("username") String username, @Query("password") String password);
}
