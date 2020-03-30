package com.btplanner.btripex.ui.main;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.Result;
import com.btplanner.btripex.data.TripRepository;
import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.Trip;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TripViewModel extends ViewModel {

    private MutableLiveData<TripFormState> tripFormState = new MutableLiveData<>();
    private MutableLiveData<AddTripResult> addTripResult = new MutableLiveData<>();
    private TripRepository tripRepository;

    TripViewModel(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public LiveData<TripFormState> getTripFormState() {
        return tripFormState;
    }

    public LiveData<AddTripResult> getAddTripResult() {
        return addTripResult;
    }

    public void addTrip(String tripId, String title, String thumbnail, String tripDestination, String tripDescription,
                        String startDate, String endDate, TripViewModel tripViewModel, LoggedInUser user) {

        tripRepository.addTrip(tripId, title, thumbnail, tripDestination, tripDescription, startDate, endDate, tripViewModel, user);
    }

    public void addTrip(Result<Trip> result) {
        if (result instanceof Result.Success) {
            Trip data = ((Result.Success<Trip>) result).getData();
            addTripResult.setValue(new AddTripResult(new TripsUserView(data, true)));
        } else {
            addTripResult.setValue(new AddTripResult(R.string.trip_add_failed));
        }
    }

    public void addTripDataChanged(String title, String thumbnail, String tripDestination, String tripDescription, String startDate, String endDate) {
        if (!isTitleNameValid(title)) {
            tripFormState.setValue(new TripFormState(R.string.invalid_title, null, null, null));
        } else if (!isDestinationValid(tripDestination)) {
            tripFormState.setValue(new TripFormState(null, R.string.invalid_destination, null, null));
        }
        // TODO: ADD DATE VALIDATION
        else {
            tripFormState.setValue(new TripFormState(true));
        }
    }

    private boolean isTitleNameValid(String title) {
        if (title == null) {
            return false;
        } else {
            return !title.trim().isEmpty();
        }
    }

    private boolean isDestinationValid(String destination) {
        return destination != null && destination.trim().length() > 5;
    }

}
