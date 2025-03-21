package com.example.baitapquatrinh.net;

import baitapquatrinh.Retrofit;
import baitapquatrinh.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://app.iotstar.vn:8081/appfoods/") // Đảm bảo URL có dấu `/` cuối
                    .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON tự động
                    .build();
        }
        return retrofit;
    }
}
