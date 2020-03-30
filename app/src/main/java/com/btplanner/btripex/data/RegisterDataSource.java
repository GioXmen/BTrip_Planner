package com.btplanner.btripex.data;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.RegisteredUser;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;

import java.io.IOException;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterDataSource {

    public void register(String username, String password, RegisterRepository registerRepository) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        RegisteredUser registeredUser = new RegisteredUser(username, password);
        Call<RegisteredUser> call = service.register(registeredUser);
        call.enqueue(new Callback<RegisteredUser>() {
            @Override
            @SuppressWarnings("unchecked")
            public void onResponse(@NonNull Call<RegisteredUser> call, @NonNull Response<RegisteredUser> response) {
                RegisteredUser newUser = response.body();
                Result<RegisteredUser> result = new Result.Success<>(newUser);
                if (newUser == null) {
                    result = new Result.Error(new IOException(String.valueOf(R.string.register_failed)));
                }
                registerRepository.register(result);
            }

            @Override
            @SuppressWarnings("unchecked")
            public void onFailure(@NonNull Call<RegisteredUser> call, @NonNull Throwable t) {
                Result<RegisteredUser> result = new Result.Error(new IOException(String.valueOf(R.string.register_call_failed), t));
                registerRepository.register(result);
            }
        });
    }
}
