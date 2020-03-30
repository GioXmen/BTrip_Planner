package com.btplanner.btripex.data;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;

import java.io.IOException;

import androidx.annotation.NonNull;
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
            @SuppressWarnings("unchecked")
            public void onResponse(@NonNull Call<LoggedInUser> call, @NonNull Response<LoggedInUser> response) {
                LoggedInUser newUser = response.body();
                Result<LoggedInUser> result = new Result.Success<>(newUser);
                if (newUser == null) {
                    result = new Result.Error(new IOException(String.valueOf(R.string.login_failed)));
                }
                loginRepository.login(result);
            }

            @Override
            @SuppressWarnings("unchecked")
            public void onFailure(@NonNull Call<LoggedInUser> call, @NonNull Throwable t) {
                Result<LoggedInUser> result = new Result.Error(new IOException(String.valueOf(R.string.login_call_failed), t));
                loginRepository.login(result);
            }
        });
    }
}
