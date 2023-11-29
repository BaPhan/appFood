package com.baphan.appdemo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.baphan.appdemo.domain.Food;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insert(Food food);


    @Query("select * from foods where name = :name")
    List<Food> checkFood(String name);

    @Query("select * from foods")
    List<Food> getListFood();

    @Update
    void updateUser(Food food);

    @Delete
    void deleteFood(Food food);

    @Query("Delete from foods")
    void deleteAllFood();

    @Query("select * from foods where name like '%' || :name || '%'")
    List<Food> searchName(String name);

}
