package com.baphan.appdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ImageView;import com.baphan.appdemo.adapter.FoodSetUpAdapter;
import com.baphan.appdemo.dao.AppDatabase;
import com.baphan.appdemo.domain.Category;
import com.baphan.appdemo.domain.Food;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.widget.TextView;import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;
public class SetUpFoodActivity extends AppCompatActivity {

    private Button btnAdd;


    private EditText edtSetUpFoodName, edtSetUpFoodDes, edtSetUpFoodCate, edtSetUpFoodPrice, edtSetUpFoodStatus;
    private TextView tvDeleteAllFood;
    private ImageButton imgCameraFood, imgFolderFood;
    private ImageView imgFood;

    private RecyclerView rcvSetUpFood;

    private static final Integer REQUEST_CODE_CAMERA = 111;
    private static final Integer REQUEST_CODE_FOLDER = 222;

    private static final int MY_REQUEST_CODE = 11;

    private List<Food> foods;

    private FoodSetUpAdapter foodSetUpAdapter;
    FirebaseStorage storage = FirebaseStorage.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_food);

        //Tạo đối tượng firebase
        StorageReference storageRef = storage.getReference();

        btnAdd = findViewById(R.id.btn_add_food);
        edtSetUpFoodName = findViewById(R.id.edt_food_name);
        edtSetUpFoodDes = findViewById(R.id.edt_food_des);
        edtSetUpFoodPrice = findViewById(R.id.edt_food_price);
        edtSetUpFoodCate = findViewById(R.id.edt_food_cate);
        edtSetUpFoodStatus = findViewById(R.id.edt_food_status);

        tvDeleteAllFood = findViewById(R.id.tv_delete_all_food);
        imgCameraFood = findViewById(R.id.img_camera_set_up_food);
        imgFood = findViewById(R.id.img_set_up_food);
        imgFolderFood = findViewById(R.id.img_folder_set_up_food);
        imgCameraFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        imgFolderFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food food = new Food();
                String name = edtSetUpFoodName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(SetUpFoodActivity.this, "Hay nhap ten mon", Toast.LENGTH_SHORT).show();
                    return;
                }
                String des = edtSetUpFoodDes.getText().toString().trim();
                if (TextUtils.isEmpty(des)) {
                    Toast.makeText(SetUpFoodActivity.this, "Hay nhap mo ta mon an", Toast.LENGTH_SHORT).show();
                    return;
                }
                Double price = Double.valueOf(edtSetUpFoodPrice.getText().toString().trim());
                if (TextUtils.isEmpty(price.toString())) {
                    Toast.makeText(SetUpFoodActivity.this, "Hay nhap gia mon", Toast.LENGTH_SHORT).show();
                    return;
                }
                String status = edtSetUpFoodStatus.getText().toString().trim();
                if (TextUtils.isEmpty(status)) {
                    Toast.makeText(SetUpFoodActivity.this, "Hay nhap trang thai mon", Toast.LENGTH_SHORT).show();
                    return;
                }
                String nameCate = edtSetUpFoodCate.getText().toString().trim();
                if (TextUtils.isEmpty(nameCate)) {
                    Toast.makeText(SetUpFoodActivity.this, "Hay nhap ten danh muc mon an", Toast.LENGTH_SHORT).show();
                    return;
                }
                food.setName(name);
                food.setDes(des);
                food.setStatus(status);
                food.setPrice(price);
                // Tim category theo name
                List<Category> categoriess = AppDatabase.getInstance(SetUpFoodActivity.this).categoryDao().searchName(nameCate);
                if(categoriess.isEmpty()){
                    Toast.makeText(SetUpFoodActivity.this, "Danh muc mon khong ton tai", Toast.LENGTH_SHORT).show();
                    return;
                }
                Category category = categoriess.get(0);
                food.setCategoryId(category.getId());
                if (imgFood == null){
                    Toast.makeText(SetUpFoodActivity.this, "Hay chon 1 anh", Toast.LENGTH_SHORT).show();
                    return;
                }
                // xu ly data

                Calendar calendar = Calendar.getInstance();

                StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
                imgFood.setDrawingCacheEnabled(true);
                imgFood.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgFood.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(SetUpFoodActivity.this, "error !!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        Toast.makeText(SetUpFoodActivity.this, "success !!!", Toast.LENGTH_SHORT).show();
                        // Lấy URI của ảnh tải lên thành công
                        StorageReference storageRef = taskSnapshot.getMetadata().getReference();
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                food.setImageUri(uri.toString());
                                // Picasso.get().load(uri).into(imgTest);
                                AppDatabase.getInstance(SetUpFoodActivity.this).foodDao().insert(food);
                                Toast.makeText(SetUpFoodActivity.this, "update success !", Toast.LENGTH_SHORT).show();

                                edtSetUpFoodName.setText("");
                                edtSetUpFoodDes.setText("");
                                edtSetUpFoodStatus.setText("");
                                edtSetUpFoodPrice.setText("");
                                edtSetUpFoodCate.setText("");
                                hideKeyBoard();
                                foods = AppDatabase.getInstance(SetUpFoodActivity.this).foodDao().getListFood();
                                foodSetUpAdapter.setData(foods);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SetUpFoodActivity.this, "get image failure !!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        // xu ly hien thi rcv
        rcvSetUpFood = findViewById(R.id.rcv_set_up_food);
        foodSetUpAdapter = new FoodSetUpAdapter(new FoodSetUpAdapter.IClickItemFoodSetUp() {
            @Override
            public void update(Food food) {
                clickUpdate(food);
            }

            @Override
            public void delete(Food food) {
                clickDelete(food);
            }
        },SetUpFoodActivity.this);
        foods = new ArrayList<>();
        foodSetUpAdapter.setData(foods);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvSetUpFood.setLayoutManager(linearLayoutManager);
        rcvSetUpFood.setAdapter(foodSetUpAdapter);
        loadData();
    }
    public void hideKeyBoard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgFood.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgFood.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void loadData() {
        foods = AppDatabase.getInstance(SetUpFoodActivity.this).foodDao().getListFood();
        foodSetUpAdapter.setData(foods);
    }
    public void clickUpdate(Food food){
//        Intent intent = new Intent(this, UpdateUserActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("obj_user",userRoomTest);
//        intent.putExtras(bundle);
//        startActivityForResult(intent,MY_REQUEST_CODE);
    }

    public void clickDelete(Food food){
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete user ? ")
                .setMessage("Are you sure!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete user
                        AppDatabase.getInstance(SetUpFoodActivity.this).foodDao().deleteFood(food);
                        Toast.makeText(SetUpFoodActivity.this, "Delete success!!", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                }).setNegativeButton("No", null)
                .show();
    }
}