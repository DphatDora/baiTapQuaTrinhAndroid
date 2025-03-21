package com.example.baitapquatrinh.utils;

import android.content.Context;
import android.content.SharedPreferences;
// 22110335 - Pham Hoang Huy
public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUser(Long userId , String email, String userName) {
        editor.putLong(KEY_USER_ID, userId);  // neu da ton tai thi no ghi de len cai userID va email cu
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USERNAME, userName);
        editor.apply();
    }


    public Long getUserId() {
        return sharedPreferences.getLong(KEY_USER_ID, -1);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public boolean isLoggedIn() {
        return getUserId() != -1;
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
