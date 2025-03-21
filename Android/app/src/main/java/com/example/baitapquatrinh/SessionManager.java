package com.example.baitapquatrinh;

import android.content.Context;
import android.content.SharedPreferences;

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

    public void saveUser(int userId, String email) {
        editor.putInt(KEY_USER_ID, userId);  // neu da ton tai thi no ghi de len cai userID va email cu
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }


    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public boolean isLoggedIn() {
        return getUserId() != -1;
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
