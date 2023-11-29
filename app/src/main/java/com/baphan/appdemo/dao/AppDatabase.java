package com.baphan.appdemo.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.baphan.appdemo.domain.Category;
import com.baphan.appdemo.domain.Food;
import com.baphan.appdemo.domain.User;

@Database(entities = {User.class, Food.class, Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "app.db";

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract UserDao userDao();

    public abstract FoodDao foodDao();

    public abstract CategoryDao categoryDao();
}
