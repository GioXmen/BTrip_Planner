package com.btplanner.btripex.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.RegisterRepository;
import com.btplanner.btripex.data.Result;
import com.btplanner.btripex.data.model.RegisteredUser;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    private RegisterRepository registerRepository;

    RegisterViewModel(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String username, String password, RegisterViewModel registerViewModel) {
        registerRepository.register(username, password, registerViewModel);
    }

    public void register(Result<RegisteredUser> result) {
        if (result instanceof Result.Success) {
            RegisteredUser data = ((Result.Success<RegisteredUser>) result).getData();
            registerResult.setValue(new RegisterResult(new RegisteredUserView(data.getUserName(), data.getPassword())));
        } else {
            registerResult.setValue(new RegisterResult(R.string.register_failed));
        }
    }

    void registerDataChanged(String username, String password, String passwordConfirm) {
        if (!isPasswordConfirmValid(password, passwordConfirm)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_password_confirm));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password, null));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    private boolean isPasswordConfirmValid(String password, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
