package com.btplanner.btripex.data;

import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.RegisteredUser;
import com.btplanner.btripex.data.model.Trip;
import com.btplanner.btripex.ui.main.TripViewModel;
import com.btplanner.btripex.ui.register.RegisterViewModel;

import java.util.Date;
import java.util.List;


/**
 * Class that requests trip information from the remote data source and
 * maintains an in-memory cache of trip information.
 */
public class TripRepository {

    private static volatile TripRepository instance;

    private TripDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private List<Trip> trips = null;
    private TripViewModel tripViewModel = null;

    // private constructor : singleton access
    private TripRepository(TripDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static TripRepository getInstance(TripDataSource dataSource) {
        if (instance == null) {
            instance = new TripRepository(dataSource);
        }
        return instance;
    }

    public boolean areTripsLoaded() {
        return trips != null;
    }

    private void setTrips(List<Trip> trips) {
        this.trips = trips;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    private void addTrip(Trip trip) {
        this.trips.add(trip);
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    private void setTripViewModel (TripViewModel tripViewModel){
        this.tripViewModel = tripViewModel;
    }

    public void addTrip(String title, String thumbnail, String tripDestination, String tripDescription,
                        String startDate, String endDate, TripViewModel tripViewModel, LoggedInUser user) {
        setTripViewModel(tripViewModel);
        dataSource.addTrip(title, thumbnail, tripDestination, tripDescription, startDate, endDate, user,  instance);
    }

    public void addTrip( Result<Trip> result) {
        if (result instanceof Result.Success) {
            addTrip(((Result.Success<Trip>) result).getData());
        }
        tripViewModel.addTrip(result);
    }
}
