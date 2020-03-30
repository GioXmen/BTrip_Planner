package com.btplanner.btripex.ui.main;

import com.btplanner.btripex.data.TripDataSource;
import com.btplanner.btripex.data.TripRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * ViewModel provider factory to instantiate TripViewModel.
 * Required given TripViewModel has a non-empty constructor
 */
public class TripViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TripViewModel.class)) {
            return (T) new TripViewModel(TripRepository.getInstance(new TripDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
