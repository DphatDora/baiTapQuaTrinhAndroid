package com.example.baitapquatrinh.api;



import com.example.baitapquatrinh.MenuItem;
import com.example.baitapquatrinh.dto.ApiResponse;
import com.example.baitapquatrinh.dto.ApiResponseLogin;
import com.example.baitapquatrinh.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    @POST("api/auth/login")
    @FormUrlEncoded
    Call<ApiResponseLogin> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/auth/register")
    Call<ApiResponse> registerUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("api/auth/verify-otp")
    Call<ApiResponse> verifyOtp(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("otp") String otp
    );

    @FormUrlEncoded
    @POST("api/auth/forgot-password")
    Call<ApiResponse> sendResetOtp(
            @Field("email") String email
    );
    @FormUrlEncoded
    @POST("api/auth/reset-password")
    Call<ApiResponse> resetPassword(
            @Field("email") String email,
            @Field("otp") String otp,
            @Field("newPassword") String newPassword
    );
    // Hà Đức Phát - 22110393
    @GET("api/item/latest-created")
    Call<List<MenuItem>> getLastestMenuItems();
    @GET("api/item/latest-price")
    Call<List<MenuItem>> getMenuItemsByPage(
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    @GET("api/cate")
    Call<List<Category>> getCategoryAll();
}
