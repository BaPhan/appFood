package com.baphan.appdemo.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "categories")
public class Category implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name;

    private Integer imageUrl;

    private String imageCateUri;

    public String getImageCateUri() {
        return imageCateUri;
    }

    public void setImageCateUri(String imageCateUri) {
        this.imageCateUri = imageCateUri;
    }

    private String description;

    public Category(String name, Integer imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Category() {
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

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
