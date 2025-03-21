package com.example.baitapquatrinh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.baitapquatrinh.HomeActivity;
import com.example.baitapquatrinh.R;
import com.example.baitapquatrinh.utils.SessionManager;
//22110335 - Pham Hoang Huy
public class IntroActivity extends AppCompatActivity {

    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        CheckUser();

    }
    private void AnhXa () {
        this.btn_start = findViewById(R.id.btn_start);
    }

    private void CheckUser() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(IntroActivity.this);
                Intent intent = new Intent();
                if (sessionManager.isLoggedIn()){
                    intent.setClass(IntroActivity.this, HomeActivity.class);
                }
                else{
                    // mo activity login
                    intent.setClass(IntroActivity.this, LoginActivity.class);
                }
                startActivity(intent);
            }
        });
    }
}