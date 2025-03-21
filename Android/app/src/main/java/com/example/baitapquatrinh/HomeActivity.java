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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private GridView gridView;
    private MenuItemAdapter menuItemAdapter;
    private List<MenuItem> menuItemList = new ArrayList<>();
    private ApiService apiService;

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
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
        menuItemAdapter = new MenuItemAdapter(this, R.layout.item_gridview, menuItemList);
        gridView.setAdapter(menuItemAdapter);

        loadMenuItem();

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
}
