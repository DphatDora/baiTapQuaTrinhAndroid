package com.example.baitapquatrinh.model;

import com.example.baitapquatrinh.MenuItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

        private Long id;

        private String type;

        private String imgCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgCategory() {
        return imgCategory;
    }

    public void setImgCategory(String imgCategory) {
        this.imgCategory = imgCategory;
    }

}
