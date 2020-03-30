package com.btplanner.btripex.ui.event;

import com.btplanner.btripex.data.EventDataSource;
import com.btplanner.btripex.data.EventRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * ViewModel provider factory to instantiate EventViewModel.
 * Required given EventViewModel has a non-empty constructor
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
