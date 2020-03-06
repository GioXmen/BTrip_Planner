package com.btplanner.btripex.ui.register;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.btplanner.btripex.data.LoginDataSource;
import com.btplanner.btripex.data.RegisterRepository;
import com.btplanner.btripex.data.RegisterDataSource;
import com.btplanner.btripex.ui.login.LoginViewModel;

public class RegisterViewModelFactory implements ViewModelProvider.Factory{

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(RegisterRepository.getInstance(new RegisterDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
