package com.example.baitapquatrinh;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.baitapquatrinh.api.APIService;
import com.example.baitapquatrinh.api.RetrofitClient;
import com.example.baitapquatrinh.model.Category;
import com.example.baitapquatrinh.layout.CategoryAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrofitActivity extends AppCompatActivity {
    private RecyclerView rcCate;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        rcCate = findViewById(R.id.rc_category);
        getCategory();
    }

    private void getCategory() {
        apiService = RetrofitClient.getInstance();
        apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();

                    if (categoryAdapter == null) {
                        categoryAdapter = new CategoryAdapter(RetrofitActivity.this, categoryList);
                        rcCate.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(getApplicationContext(),
                                        LinearLayoutManager.HORIZONTAL, false);

                        rcCate.setLayoutManager(layoutManager);
                        rcCate.setAdapter(categoryAdapter);
                    } else {
                        categoryAdapter.updateData(categoryList);
                    }
                } else {
                    Toast.makeText(RetrofitActivity.this, "Lỗi lấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("logg", t.getMessage());
                Toast.makeText(RetrofitActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
