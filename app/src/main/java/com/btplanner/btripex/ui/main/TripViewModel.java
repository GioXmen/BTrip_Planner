package com.btplanner.btripex.ui.main;

import android.annotation.SuppressLint;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.Result;
import com.btplanner.btripex.data.TripRepository;
import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

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
        if (isTripFieldEmpty(title)) {
            tripFormState.setValue(new TripFormState(R.string.invalid_title, null, null, null));
        } else if (isTripFieldEmpty(tripDestination)) {
            tripFormState.setValue(new TripFormState(null, R.string.invalid_destination, null, null));
        } else if (startDate != null && endDate != null  && isDatePeriodInValid(startDate, endDate)) {
            tripFormState.setValue(new TripFormState(null, null, R.string.date_period_invalid, null));
        }
        else {
            tripFormState.setValue(new TripFormState(true));
        }
    }

    private boolean isTripFieldEmpty(String title) {
        if (title == null) {
            return true;
        } else {
            return title.trim().isEmpty();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static boolean isDatePeriodInValid(String start, String end) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);
            return startDate.after(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }



}
