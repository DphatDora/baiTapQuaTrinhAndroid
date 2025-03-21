package com.example.baitapquatrinh;

import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baitapquatrinh.api.APIService;
import com.example.baitapquatrinh.api.RetrofitClient;
import com.example.baitapquatrinh.layout.CategoryAdapter;
import com.example.baitapquatrinh.model.Category;
import com.example.baitapquatrinh.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rcCate;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    private GridView gridView;
    private MenuItemAdapter menuItemAdapter;
    private List<MenuItem> menuItemList = new ArrayList<>();
    private APIService apiService;

    private TextView txtName, txtEmail;
    private ImageView imgAvatar;

    private boolean isLoading = false;
    private int page = 1;
    private static final int PAGE_SIZE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        imgAvatar = findViewById(R.id.imgAvatar);

        gridView = findViewById(R.id.gridMenuItem);
        apiService = RetrofitClient.getInstance();
        menuItemAdapter = new MenuItemAdapter(this, R.layout.item_gridview, menuItemList);
        gridView.setAdapter(menuItemAdapter);

        loadMenuItem();

        rcCate = findViewById(R.id.rc_category);
        getCategory();

        // Tải dữ liệu trang đầu tiên - Trần Thanh Nhã
        SessionManager sessionManager = new SessionManager(HomeActivity.this);
        String name = sessionManager.getUsername();
        String email = sessionManager.getUserEmail();


        String avatarUrl = "";

        // Gán dữ liệu lên giao diện
        txtName.setText("Hi! " + name);
        txtEmail.setText(email);

        // Hiển thị ảnh với Glide
        Glide.with(this)
                .load(avatarUrl)
                .error(R.drawable.ic_error) // Ảnh lỗi nếu không tải được
                .into(imgAvatar);

        // Lắng nghe sự kiện cuộn để lazy loading
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isLoading && (firstVisibleItem + visibleItemCount >= totalItemCount) && totalItemCount > 0) {
                    page++; // Tăng số trang
                    loadMenuItem(); // Tải thêm dữ liệu
                }
            }
        });
    }
// Hà Đức Phát - 22110393 : load product to grid view lazy loading
    private void loadMenuItem() {
        isLoading = true; // Đánh dấu đang tải dữ liệu
        apiService.getMenuItemsByPage(page, PAGE_SIZE).enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MenuItem> newItems = response.body();
                    menuItemList.addAll(newItems); // Thêm dữ liệu mới vào danh sách
                    menuItemAdapter.notifyDataSetChanged(); // Cập nhật GridView
                } else {
                    Toast.makeText(HomeActivity.this, "Không có dữ liệu mới", Toast.LENGTH_SHORT).show();
                }
                isLoading = false; // Kết thúc tải
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", Objects.requireNonNull(t.getMessage()));
                isLoading = false;
            }
        });
    }
    private void getCategory() {
        apiService = RetrofitClient.getInstance();
        apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();

                    if (categoryAdapter == null) {
                        categoryAdapter = new CategoryAdapter(HomeActivity.this, categoryList);
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
                    Toast.makeText(HomeActivity.this, "Lỗi lấy dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("logg>>>>", t.getMessage());
                Toast.makeText(HomeActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
