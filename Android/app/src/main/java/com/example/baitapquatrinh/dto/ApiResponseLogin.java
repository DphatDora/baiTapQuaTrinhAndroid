package com.example.baitapquatrinh.dto;

import com.example.baitapquatrinh.entity.User;

public class ApiResponseLogin {
    private String status;
    private String message;
    private User user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ApiResponseLogin(String status, String message, User user) {
        this.status = status;
        this.message = message;
        this.user = user;
    }
}
