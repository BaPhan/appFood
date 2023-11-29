package com.baphan.appdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SetUpActivity extends AppCompatActivity {

    Button btnSetUpCate,btnSetUpFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_setup);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final int home = R.id.action_home;
                if (item.getItemId() == R.id.action_home) {
                    Intent intent = new Intent(SetUpActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_cart) {
                } else if (item.getItemId() == R.id.action_setup) {
                }
                return true;
            }
        });

        btnSetUpCate = findViewById(R.id.btn_set_up_cate);
        btnSetUpFood = findViewById(R.id.btn_set_up_food);

        btnSetUpCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetUpActivity.this,SetUpCategoryActivity.class);
                startActivity(intent);
            }
        });
        btnSetUpFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetUpActivity.this, SetUpFoodActivity.class);
                startActivity(intent);
            }
        });
    }
}