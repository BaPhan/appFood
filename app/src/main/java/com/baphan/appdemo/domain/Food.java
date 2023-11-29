package com.baphan.appdemo.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "foods")
public class Food implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name;
    private String des;
    private String status;

    private Double price;

    private Integer categoryId;

    private Integer menuId;

    private Integer imageUrl;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    private String imageUri;

    public Food( String name, String status, Double price,Integer imageUrl) {
        this.name = name;
        this.status = status;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Food() {
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
