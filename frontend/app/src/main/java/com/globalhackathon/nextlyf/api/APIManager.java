package com.globalhackathon.nextlyf.api;

import android.util.Log;

import com.globalhackathon.nextlyf.listeners.ChatResponseListener;
import com.globalhackathon.nextlyf.listeners.OTPResponseListener;
import com.globalhackathon.nextlyf.listeners.RecommendationListener;
import com.globalhackathon.nextlyf.model.ChatRequest;
import com.globalhackathon.nextlyf.model.ChatResponse;
import com.globalhackathon.nextlyf.model.OTPResponse;
import com.globalhackathon.nextlyf.model.RecommendationRequest;
import com.globalhackathon.nextlyf.model.RecommendationResponse;
import com.globalhackathon.nextlyf.model.SendOTPRequest;
import com.globalhackathon.nextlyf.model.UserDetails;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {


    private APIServiceInterface apiServiceInterface;

    private Retrofit retrofit;

    private static Map<String, APIServiceInterface> retrofitMap = new HashMap();

    Gson gson;
    public static final String BACKEND_BASE_URL = "http://10.39.31.164:8080";
    public static final String ML_BASE_URL = "http://192.168.0.100:5000";

    private static Map<String, String> urlMap = new HashMap();


    public APIManager() {
    }
    // Create a singleton instance of Retrofit
    public APIManager(String baseUrl) {
        if(baseUrl.equals("custom")) {
            baseUrl = urlMap.getOrDefault("custom", ML_BASE_URL);
        }
        if (retrofitMap == null || retrofitMap.get(baseUrl) == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okhttp = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttp)
                    .build();
            gson = new Gson();

            apiServiceInterface = retrofit.create(APIServiceInterface.class);

            retrofitMap.put(baseUrl, apiServiceInterface);
        }else {
            apiServiceInterface = retrofitMap.get(baseUrl);
        }
    }

    public void setCustomUrl(String customUrl) {
        urlMap.put("custom", customUrl);
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

    public void converse(ChatRequest chatRequest, ChatResponseListener chatResponseListener) {

        Call<ChatResponse> call = apiServiceInterface.converse(chatRequest);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful()) {
                    ChatResponse chatResponse = response.body();
                    Log.i("Chat API Response", "Success");
                    chatResponseListener.onSuccess(chatResponse.getResponse());
                } else {
                    Log.i("Chat API Response", "Failed");
                    chatResponseListener.onFailure(null);
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                Log.i("Chat API Response", "Failed " + t.getMessage());
                chatResponseListener.onFailure(null);
            }
        });
    }

    public void getRecommendation(RecommendationRequest recommendationRequest,
                                  RecommendationListener recommendationListener) throws Exception{

        Call<RecommendationResponse> call = apiServiceInterface.getRecommendation(recommendationRequest);

        call.enqueue(new Callback<RecommendationResponse>() {
            @Override
            public void onResponse(Call<RecommendationResponse> call, Response<RecommendationResponse> response) {
                if (response.isSuccessful()) {
                    RecommendationResponse recommendationResponse = response.body();
                    Log.i("Recommendation API Response", "Success");
                    recommendationListener.onRecommendationSuccess(recommendationResponse.getRecommendations());
                } else {
                    Log.i("Recommendation API Response", "Failed");
                    recommendationListener.onRecommendationFailure(null);
                }
            }

            @Override
            public void onFailure(Call<RecommendationResponse> call, Throwable t) {
                Log.i("Recommendation API Response", "Failed " + t.getMessage());
                recommendationListener.onRecommendationFailure(null);
            }
        });




    }


}
