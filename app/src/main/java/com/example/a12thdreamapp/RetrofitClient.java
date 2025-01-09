package com.example.a12thdreamapp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api-football-v1.p.rapidapi.com/";
    private static final String RAPID_API_KEY = "1a495e428d8895619beb8432c5bfc19e";
    private static final String RAPID_API_HOST = "api-football-v1.p.rapidapi.com";
    private static RetrofitClient instance = null;
    private static Retrofit retrofit;

    private RetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("x-rapidapi-key", RAPID_API_KEY)
                            .header("x-rapidapi-host", RAPID_API_HOST)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
