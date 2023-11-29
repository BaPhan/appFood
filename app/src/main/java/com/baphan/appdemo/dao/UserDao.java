package com.baphan.appdemo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.baphan.appdemo.domain.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("select * from users where username = :username and password = :password")
    User login(String username, String password);

    @Query("select * from users where username = :username")
    List<User> checkUser(String username);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("Delete from users")
    void deleteAllUser();

    @Query("select * from users where username like '%' || :name || '%'")
    List<User> searchName(String name);

    @Insert
    void insertUser(User user);
}
