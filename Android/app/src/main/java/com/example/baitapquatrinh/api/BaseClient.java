package com.example.baitapquatrinh.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseClient {
    private static final HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    static <S> S createService(String baseUrl, Class<S> serviceClass) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        if(!httpClient.interceptors().contains(logging)){
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}
