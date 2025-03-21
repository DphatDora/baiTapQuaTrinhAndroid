package com.example.baitapquatrinh.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baitapquatrinh.HomeActivity;
import com.example.baitapquatrinh.R;
import com.example.baitapquatrinh.api.APIService;
import com.example.baitapquatrinh.api.RetrofitClient;
import com.example.baitapquatrinh.dto.ApiResponseLogin;
import com.example.baitapquatrinh.entity.User;
import com.example.baitapquatrinh.utils.SessionManager;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//22110333 - Nguyen Sang Huy

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    ImageView btnLogin;
    APIService apiService;
    SessionManager prefManager;
    private TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
//        textView = findViewById(R.id.tvRegister);
//        textView.setOnClickListener(v -> {
//            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
//            startActivity(intent);
//        });
        TextView tvRegister = findViewById(R.id.tvRegister);
        etUsername = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnImage);

        apiService = RetrofitClient.getInstance();
        prefManager = new SessionManager(this);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }

            private void loginUser() {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                btnLogin.setEnabled(false);

                Call<ApiResponseLogin> call = apiService.login(username, password);

                call.enqueue(new Callback<ApiResponseLogin>() {
                    @Override
                    public void onResponse(Call<ApiResponseLogin> call, Response<ApiResponseLogin> response) {
                        btnLogin.setEnabled(true);

                        if (response.isSuccessful() && response.body() != null) {
                           User user = response.body().getUser();

                            // Lưu token vào SharedPreferences
                            prefManager.saveUser(user.getId(), user.getEmail(), user.getUsername());

                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }else if (response.code() == 400) {
                            try {
                                String errorBody = response.errorBody().string();
                                JSONObject jsonObject = new JSONObject(errorBody);
                                String message = jsonObject.getString("message");
                                Toast.makeText(LoginActivity.this,  message, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponseLogin> call, Throwable t) {
                        try {
                            btnLogin.setEnabled(true);
                            Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                });
            }
        });
    }
}
