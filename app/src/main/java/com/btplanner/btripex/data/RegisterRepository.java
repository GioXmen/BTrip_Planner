package com.btplanner.btripex.data;

import com.btplanner.btripex.data.model.RegisteredUser;
import com.btplanner.btripex.ui.register.RegisterViewModel;

/**
 * Class that requests authentication and user information from the remote data source
 */
public class RegisterRepository {

    private static volatile RegisterRepository instance;

    private RegisterDataSource dataSource;
    private RegisteredUser user = null;
    private RegisterViewModel registerViewModel = null;

    private RegisterRepository(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegisterRepository getInstance(RegisterDataSource dataSource) {
        if (instance == null) {
            instance = new RegisterRepository(dataSource);
        }
        return instance;
    }

    public boolean isRegistered() {
        return user != null;
    }

    private void setRegisteredUser(RegisteredUser user) {
        this.user = user;
    }

    private void setRegisterViewModel(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
    }

    public void register(String username, String password, RegisterViewModel registerViewModel) {
        setRegisterViewModel(registerViewModel);
        dataSource.register(username, password, instance);
    }

    public void register(Result<RegisteredUser> result) {
        if (result instanceof Result.Success) {
            setRegisteredUser(((Result.Success<RegisteredUser>) result).getData());
        }
        registerViewModel.register(result);
    }
}
