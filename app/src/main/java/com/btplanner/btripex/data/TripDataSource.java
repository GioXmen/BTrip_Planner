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

    public void addTrip(String tripId, String title, String thumbnail, String tripDestination, String tripDescription,
                        String startDate, String endDate, LoggedInUser user, TripRepository tripRepository) {

        Trip newTrip = new Trip(tripId, title, thumbnail, tripDestination, tripDescription, startDate, endDate, user);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Trip> call = service.addTrip(newTrip);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                Trip newTrip = response.body();
                Result<Trip> result = new Result.Success<>(newTrip);
                if (newTrip == null){
                    result = new Result.Error(new IOException("Error adding/updating trip"));
                }
                tripRepository.addTrip(result);
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                Result<Trip> result = new Result.Error(new IOException("Error adding/updating trip", t));
                tripRepository.addTrip(result);
            }
        });
    }
}
