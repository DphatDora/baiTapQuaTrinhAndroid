package com.example.baitapquatrinh.api;



import com.example.baitapquatrinh.dto.ApiResponseLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @POST("api/auth/login")
    @FormUrlEncoded
    Call<ApiResponseLogin> login(@Field("email") String email, @Field("password") String password);
}
