package com.btplanner.btripex.ui.event;

import com.btplanner.btripex.data.EventDataSource;
import com.btplanner.btripex.data.EventRepository;
import com.btplanner.btripex.data.TripDataSource;
import com.btplanner.btripex.data.TripRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * ViewModel provider factory to instantiate TripViewModel.
 * Required given TripViewModel has a non-empty constructor
 */
public class EventViewModelFactory implements ViewModelProvider.Factory  {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EventViewModel.class)) {
            return (T) new EventViewModel(EventRepository.getInstance(new EventDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
