package com.btplanner.btripex.data;

import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.ui.login.LoginViewModel;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;
    private LoggedInUser id = null;
    private LoginViewModel loginViewModel = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        id = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public LoggedInUser getLoggedInUser() {
        return user;
    }

    private void setLoginViewModel (LoginViewModel loginViewModel){
        this.loginViewModel = loginViewModel;
    }

    public void login(String username, String password, LoginViewModel loginViewModel) {
        setLoginViewModel(loginViewModel);
        dataSource.login(username, password, instance);
    }

    public void login( Result<LoggedInUser> result) {
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        loginViewModel.login(result);
    }
}
