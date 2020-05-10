package com.btplanner.btripex.data;

import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.Trip;
import com.btplanner.btripex.ui.main.TripViewModel;

import java.util.List;

/**
 * Class that requests/updates Trip information from the remote data source
 */
public class TripRepository {

    private static volatile TripRepository instance;

    private TripDataSource dataSource;
    private List<Trip> trips = null;
    private TripViewModel tripViewModel = null;

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
    }

    private void addTrip(Trip trip) {
        this.trips.add(trip);
    }

    private void setTripViewModel(TripViewModel tripViewModel) {
        this.tripViewModel = tripViewModel;
    }

    public void addTrip(String tripId, String title, String thumbnail, String tripDestination, String tripDescription,
                        String startDate, String endDate, TripViewModel tripViewModel, LoggedInUser user) {
        setTripViewModel(tripViewModel);
        dataSource.addTrip(tripId, title, thumbnail, tripDestination, tripDescription, startDate, endDate, user, instance);
    }

    void addTrip(Result<Trip> result) {
        if (result instanceof Result.Success) {
            if (trips != null) {
                addTrip(((Result.Success<Trip>) result).getData());
            }
        }
        tripViewModel.addTrip(result);
    }

    public void removeTrip(String tripId, TripViewModel tripViewModel) {
        setTripViewModel(tripViewModel);
        dataSource.removeTrip(tripId, instance);
    }

    void removeTrip(Result<Void> result) {
        tripViewModel.removeTrip(result);
    }
}
