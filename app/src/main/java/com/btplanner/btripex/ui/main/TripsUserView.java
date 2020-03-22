package com.btplanner.btripex.ui.main;

import com.btplanner.btripex.data.model.Trip;

import java.util.List;

/**
 * Class exposing trips to the UI.
 */
public class TripsUserView {
    private List<Trip> trips;
    private Trip trip;

    public TripsUserView(List<Trip> trips) {
        this.trips = trips;
    }

    public TripsUserView(Trip trip, boolean addOrRemove) {
        if (addOrRemove){
            this.trip = trip;
            this.trips.add(trip);
        } else this.trips.remove(trip);
    }

    public List<Trip> getTrips(){
        return trips;
    }

    public Trip getAddedTrip(){
        return trip;
    }
}

