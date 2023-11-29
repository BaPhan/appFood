package com.baphan.appdemo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.baphan.appdemo.domain.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insert(Category category);


    @Query("select * from categories where name = :name")
    List<Category> checkCategory(String name);
    @Query("select * from categories where id = :id")
    Category findById(Integer id);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCate(Category category);

    @Query("Delete from categories")
    void deleteAllCate();

    @Query("select * from categories where name like '%' || :name || '%'")
    List<Category> searchName(String name);

    @Query("select * from categories")
    List<Category> getListCategory();

}
