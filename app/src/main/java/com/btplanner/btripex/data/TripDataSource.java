package com.btplanner.btripex.data;

import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.Trip;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDataSource {

    public void addTrip(String title, String thumbnail, String tripDestination, String tripDescription,
                        String startDate, String endDate, LoggedInUser user, TripRepository tripRepository) {

        Trip newTrip = new Trip(title, thumbnail, tripDestination, tripDescription, startDate, endDate, user);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Trip> call = service.addTrip(newTrip);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                Trip newUser = response.body();
                Result<Trip> result = new Result.Success<>(newUser);
                if (newUser == null){
                    result = new Result.Error(new IOException("Error logging in"));
                }
                tripRepository.addTrip(result);
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                Result<Trip> result = new Result.Error(new IOException("Error logging in", t));
                tripRepository.addTrip(result);
            }
        });
    }
}
