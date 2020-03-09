package com.btplanner.btripex.data;

import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.RegisteredUser;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;

import java.io.IOException;

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
            public void onResponse(Call<RegisteredUser> call, Response<RegisteredUser> response) {
                RegisteredUser newUser = response.body();
                Result<RegisteredUser> result = new Result.Success<>(newUser);
                if (newUser == null){
                    result = new Result.Error(new IOException("Error during registration"));
                }
                registerRepository.register(result);
            }

            @Override
            public void onFailure(Call<RegisteredUser> call, Throwable t) {
                Result<RegisteredUser> result = new Result.Error(new IOException("Error during registration", t));
                registerRepository.register(result);
            }
        });
    }
}
