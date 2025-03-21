//22110455 - Nguyen Manh Tu
package com.example.baitapquatrinh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.baitapquatrinh.R;
import com.example.baitapquatrinh.api.APIService;
import com.example.baitapquatrinh.api.RetrofitClient;
import com.example.baitapquatrinh.dto.ApiResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private APIService apiService;
    private EditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText, otpEditText;

    private TextView loginTextView, timerTextView;
    private Button getOtpButton;
    private ImageButton signUpButton;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        apiService = RetrofitClient.getInstance();

        usernameEditText = findViewById(R.id.name_input);
        emailEditText = findViewById(R.id.email_input);
        passwordEditText = findViewById(R.id.password_input);
        confirmPasswordEditText = findViewById(R.id.confirmpassword_input);
        otpEditText = findViewById(R.id.otp_input);

        loginTextView = findViewById(R.id.login_text);
        timerTextView = findViewById(R.id.timerTextView);

        getOtpButton = findViewById(R.id.getOtpButton);
        signUpButton = findViewById(R.id.signupButton);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getOtpButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Vui lòng nhập tất cả thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            registerUser(username, email, password);
        });

        signUpButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String otp = otpEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || otp.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Vui lòng nhập tất cả thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            verifyOtp(username, email, password, otp);
        });

        loginTextView.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser(String username, String email, String password) {
        Call<ApiResponse> call = apiService.registerUser(username, email, password);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.getStatus().equals("success")) {
                        Toast.makeText(RegisterActivity.this, "Vui lòng kiểm tra Email", Toast.LENGTH_LONG).show();
                        // Hiển thị trường nhập OTP nếu gửi OTP thành công
                        // otpEditText.setVisibility(View.VISIBLE);

                        getOtpButton.setEnabled(false);
                        startCountdownTimer();
                    }


                } else if (response.code() == 400) {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String message = jsonObject.getString("message");
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Lỗi:  " + response.message(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(RegisterActivity.this, "aaaaaa: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RegisterError", t.getMessage());
            }
        });
    }

    private void verifyOtp(String username, String email, String password, String otp) {
        Call<ApiResponse> call = apiService.verifyOtp(username, email, password, otp);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                    if (apiResponse.getStatus().equals("success")) {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else if (response.code() == 400) {
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody);
                        String message = jsonObject.getString("message");
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Lỗi:  " + response.message(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(RegisterActivity.this, "aaaaaa: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lôi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("VerifyOtpError", t.getMessage());
            }
        });
    }
    private void startCountdownTimer() {
        if (isTimerRunning && countDownTimer != null) {
            countDownTimer.cancel(); // Hủy bộ đếm cũ nếu đang chạy
        }

        isTimerRunning = true;
        timerTextView.setVisibility(View.VISIBLE); // Hiển thị TextView đếm ngược

        countDownTimer = new CountDownTimer(60000, 1000) { // 60 giây, cập nhật mỗi 1 giây
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                timerTextView.setText(secondsRemaining + "s");
            }

            @Override
            public void onFinish() {
                timerTextView.setVisibility(View.GONE); // Ẩn TextView khi đếm ngược xong
                getOtpButton.setEnabled(true); // Kích hoạt lại nút Get OTP
                isTimerRunning = false;
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Hủy bộ đếm khi Activity bị hủy
        }
    }
}
