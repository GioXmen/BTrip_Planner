package com.btplanner.btripex.data.network;

import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.RegisteredUser;
import com.btplanner.btripex.data.model.Trip;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/user/login")
    Call<LoggedInUser> login(@Query("username") String username, @Query("password") String password);

    @POST("/user/registration")
    Call<RegisteredUser> register(@Body RegisteredUser registeredUser);

    @POST("/trip/add")
    Call<Trip> addTrip(@Body Trip trip);

    @GET("/trip/get")
    Call<List<Trip>> getAllTrips(@Query("username") String username, @Query("password") String password);

    @POST("/event/add")
    Call<Event> addEvent(@Body Event event);

    @GET("/event/get")
    Call<List<Event>> getAllEvents(@Query("tripId") String id);
}
