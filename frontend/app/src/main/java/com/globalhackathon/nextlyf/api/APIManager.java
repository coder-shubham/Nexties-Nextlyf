package com.globalhackathon.nextlyf.api;

import android.util.Log;

import com.globalhackathon.nextlyf.listeners.OTPResponseListener;
import com.globalhackathon.nextlyf.model.OTPResponse;
import com.globalhackathon.nextlyf.model.SendOTPRequest;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {


    private static APIServiceInterface apiServiceInterface;

    private static Retrofit retrofit;

    Gson gson;
    private static final String BASE_URL = "http://192.168.224.20:5000/";

    // Create a singleton instance of Retrofit
    public APIManager() {
        if (retrofit == null || apiServiceInterface == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okhttp = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttp)
                    .build();
            gson = new Gson();

            apiServiceInterface = retrofit.create(APIServiceInterface.class);
        }
    }

    public void sendOTPToUserEmail(String email, OTPResponseListener otpResponseListener) {

        SendOTPRequest sendOTPRequest = new SendOTPRequest(email);

        Call<OTPResponse> call = apiServiceInterface.sendOtp(sendOTPRequest);

        call.enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                if (response.isSuccessful()) {
                    OTPResponse otpResponse = response.body();
                    Log.i("OTP API Response", "Success");
                    otpResponseListener.onResponse(otpResponse);
                } else {
                    Log.i("OTP API Response", "Failed");
                    otpResponseListener.onResponse(null);
                }
            }

            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {
                Log.i("OTP API Response", "Failed " + t.getMessage());
                otpResponseListener.onResponse(null);
            }
        });

        // Send OTP to user email

    }


}
