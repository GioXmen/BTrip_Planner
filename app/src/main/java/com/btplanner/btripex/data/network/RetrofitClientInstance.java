package com.btplanner.btripex.data.network;

import com.btplanner.btripex.BtripApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static String BASE_URL = "http://192.168.1.101:8090";
    private static Retrofit retrofit;
    private static BtripApplication btripApplication;

    public static Retrofit getRetrofitInstance() {
        if(BtripApplication.getServerUrl() != null){
            BASE_URL = "http://" + BtripApplication.getServerUrl() + ":8090";
        }

        if (retrofit == null || !retrofit.baseUrl().toString().equals(BASE_URL + "/")) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            OkHttpClient okhttpclient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .callFactory(okhttpclient)
                    .build();
        }
        return retrofit;
    }
}
