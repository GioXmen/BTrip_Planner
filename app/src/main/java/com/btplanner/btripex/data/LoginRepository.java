package com.btplanner.btripex.data;

import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.ui.login.LoginViewModel;

/**
 * Class that requests authentication and user information from the remote data source
 */
public class LoginRepository {

    private static volatile LoginRepository instance;
    private LoginDataSource dataSource;
    private LoggedInUser user = null;
    private LoginViewModel loginViewModel = null;

    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public LoggedInUser getLoggedInUser() {
        return user;
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }

    private void setLoginViewModel(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    public void login(String username, String password, LoginViewModel loginViewModel) {
        setLoginViewModel(loginViewModel);
        dataSource.login(username, password, instance);
    }

    public void login(Result<LoggedInUser> result) {
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        loginViewModel.login(result);
    }
}
