package com.btplanner.btripex.data;

import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public void login(String username, String password, LoginRepository loginRepository) {

            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<LoggedInUser> call = service.login(username, password);
            call.enqueue(new Callback<LoggedInUser>() {
                @Override
                public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                    LoggedInUser newUser = response.body();
                    Result<LoggedInUser> result = new Result.Success<>(newUser);
                    if (newUser == null){
                        result = new Result.Error(new IOException("Error logging in"));
                    }
                    loginRepository.login(result);
                }

                @Override
                public void onFailure(Call<LoggedInUser> call, Throwable t) {
                    Result<LoggedInUser> result = new Result.Error(new IOException("Error logging in", t));
                    loginRepository.login(result);
                }
            });
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
