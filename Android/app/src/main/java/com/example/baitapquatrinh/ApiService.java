package com.example.baitapquatrinh;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    // Hà Đức Phát - 22110393
    @GET("item/latest-created")
    Call<List<MenuItem>> getLastestMenuItems();
    @GET("item/latest-price")
    Call<List<MenuItem>> getMenuItemsByPage(
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

}
