package com.example.baitapquatrinh;
// Hà Đức Phát - 22110393
public class MenuItem {
    private Long id;
    private String name;
    private double price;
    private int soldQuantity;
    private String createDate;
    private String imgMenuItem;
    private Long categoryId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getImgMenuItem() {
        return imgMenuItem;
    }

    public void setImgMenuItem(String imgMenuItem) {
        this.imgMenuItem = imgMenuItem;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
