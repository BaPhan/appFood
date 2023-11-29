package com.baphan.appdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.baphan.appdemo.adapter.CategoryAdapter;
import com.baphan.appdemo.adapter.FoodAdapter;
import com.baphan.appdemo.dao.AppDatabase;
import com.baphan.appdemo.domain.Category;
import com.baphan.appdemo.domain.Food;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvFood, rcvCate;
    private TextView tvFood, tvCate, tvFoodApp;

    private List<Food> foods;

    private List<Category> categories;
    private FoodAdapter foodAdapter;

    private CategoryAdapter categoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final int home = R.id.action_home;
                if (item.getItemId() == R.id.action_home) {
                    Toast.makeText(MainActivity.this, "home click", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.action_cart) {
                    Toast.makeText(MainActivity.this, "cart click", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.action_setup) {
                    Intent intent = new Intent(MainActivity.this,SetUpActivity.class);
                    startActivity(intent);
                }
                // Đặt màu sắc cho icon và chữ
                for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                    MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
                    boolean isChecked = menuItem.getItemId() == item.getItemId();
                    menuItem.setChecked(isChecked); // Đặt trạng thái chọn
                    // Đặt màu sắc cho icon
                    int iconColor = isChecked ? R.color.orange : R.color.black;
                    Drawable icon = menuItem.getIcon();
                    icon.setColorFilter(ContextCompat.getColor(MainActivity.this, iconColor), PorterDuff.Mode.SRC_IN);

                    // Đặt màu sắc cho chữ
                    int textColor = isChecked ? R.color.orange : R.color.black;
                    SpannableString spannableString = new SpannableString(menuItem.getTitle());
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(MainActivity.this, textColor)), 0, spannableString.length(), 0);
                    menuItem.setTitle(spannableString);
                }
                return true;
            }

        });

        tvFood = findViewById(R.id.tv_food_n);
        tvCate = findViewById(R.id.tv_cate);
        tvFoodApp = findViewById(R.id.tv_food_app);
        rcvFood = findViewById(R.id.rvc_food);
        rcvCate = findViewById(R.id.rcv_cate);
        foodAdapter = new FoodAdapter(new FoodAdapter.IClickItemFood() {
            @Override
            public void click(Food food) {
                handleClickFood(food);
            }
        });

        categoryAdapter = new CategoryAdapter(new CategoryAdapter.IClickItemCategory() {
            @Override
            public void click(Category category) {
                handleClickCate(category);
            }
        });
        // data food
        foods = new ArrayList<>();
        if (foods.isEmpty()) {
            foods.add(new Food("Mon 1", "Con", 12.00, R.drawable.asiafood1));
            foods.add(new Food("Mon 2", "Con", 13.00, R.drawable.asiafood2));
            foods.add(new Food("Mon 3", "Con", 14.00, R.drawable.asiafood1));
            foods.add(new Food("Mon 4", "Con", 15.00, R.drawable.asiafood2));

            foods.add(new Food("Mon 1", "Con", 12.00, R.drawable.asiafood1));
            foods.add(new Food("Mon 2", "Con", 13.00, R.drawable.asiafood2));
            foods.add(new Food("Mon 3", "Con", 14.00, R.drawable.asiafood1));
            foods.add(new Food("Mon 4", "Con", 15.00, R.drawable.asiafood2));

            foods.add(new Food("Mon 1", "Con", 12.00, R.drawable.asiafood1));
            foods.add(new Food("Mon 2", "Con", 13.00, R.drawable.asiafood2));
            foods.add(new Food("Mon 3", "Con", 14.00, R.drawable.asiafood1));
            foods.add(new Food("Mon 4", "Con", 15.00, R.drawable.asiafood2));

            foods.add(new Food("Mon 1", "Con", 12.00, R.drawable.asiafood1));
            foods.add(new Food("Mon 2", "Con", 13.00, R.drawable.asiafood2));
            foods.add(new Food("Mon 3", "Con", 14.00, R.drawable.asiafood1));
            foods.add(new Food("Mon 4", "Con", 15.00, R.drawable.asiafood2));
        }
        foodAdapter.setData(foods);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvFood.setLayoutManager(linearLayoutManager);
        rcvFood.setAdapter(foodAdapter);

        // data category
        categories = new ArrayList<>();
        if (categories.isEmpty()){
            categories.add(new Category("Category 1",R.drawable.popularfood1));
            categories.add(new Category("Category 2",R.drawable.popularfood2));
            categories.add(new Category("Category 3",R.drawable.popularfood3));

            categories.add(new Category("Category 1",R.drawable.popularfood1));
            categories.add(new Category("Category 2",R.drawable.popularfood2));
            categories.add(new Category("Category 3",R.drawable.popularfood3));

            categories.add(new Category("Category 1",R.drawable.popularfood1));
            categories.add(new Category("Category 2",R.drawable.popularfood2));
            categories.add(new Category("Category 3",R.drawable.popularfood3));

            categories.add(new Category("Category 1",R.drawable.popularfood1));
            categories.add(new Category("Category 2",R.drawable.popularfood2));
            categories.add(new Category("Category 3",R.drawable.popularfood3));
        }
        categoryAdapter.setData(categories);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvCate.setLayoutManager(linearLayoutManager2);
        rcvCate.setAdapter(categoryAdapter);

        //loadData();
    }

    private void loadData() {
        foods = AppDatabase.getInstance(this).foodDao().getListFood();
        foodAdapter.setData(foods);
        categories = AppDatabase.getInstance(this).categoryDao().getListCategory();
        categoryAdapter.setData(categories);
    }

    public void handleClickFood(Food food) {
        Intent intent = new Intent(this, FoodDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_food",food);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void handleClickCate(Category category){

    }
}