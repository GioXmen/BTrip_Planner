package com.btplanner.btripex.data;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.Trip;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;

import java.io.IOException;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDataSource {

    void addTrip(String tripId, String title, String thumbnail, String tripDestination, String tripDescription,
                 String startDate, String endDate, LoggedInUser user, TripRepository tripRepository) {

        Trip newTrip = new Trip(tripId, title, thumbnail, tripDestination, tripDescription, startDate, endDate, user);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Trip> call = service.addTrip(newTrip);
        call.enqueue(new Callback<Trip>() {
            @Override
            @SuppressWarnings("unchecked")
            public void onResponse(@NonNull Call<Trip> call, @NonNull Response<Trip> response) {
                Trip newTrip = response.body();
                Result<Trip> result = new Result.Success<>(newTrip);
                if (newTrip == null) {
                    result = new Result.Error(new IOException(String.valueOf(R.string.trip_add_failed)));
                }
                tripRepository.addTrip(result);
            }

            @Override
            @SuppressWarnings("unchecked")
            public void onFailure(@NonNull Call<Trip> call, @NonNull Throwable t) {
                Result<Trip> result = new Result.Error(new IOException(String.valueOf(R.string.trip_call_failed), t));
                tripRepository.addTrip(result);
            }
        });
    }
}
