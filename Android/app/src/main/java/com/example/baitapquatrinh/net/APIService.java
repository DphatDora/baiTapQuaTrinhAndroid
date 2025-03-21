package com.example.baitapquatrinh.net;

import java.util.List;
import baitapquatrinh.Call;
import baitapquatrinh.http.GET;
import com.example.baitapquatrinh.model.Category;

public interface APIService {
    @GET("categories.php")
    Call<List<Category>> getCategoryAll();
}
