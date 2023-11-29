package com.baphan.appdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baphan.appdemo.adapter.CateSetUpAdapter;
import com.baphan.appdemo.dao.AppDatabase;
import com.baphan.appdemo.domain.Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SetUpCategoryActivity extends AppCompatActivity {
    private Button btnAdd;

    private EditText edtSetUpCateName, edtSetUpCateDes;
    private TextView tvDeleteAllCate, tvTest;
    private ImageButton imgCameraCate, imgFolderCate;
    private ImageView imgCate, imgTest;

    private RecyclerView rcvSetUpCate;

    private static final Integer REQUEST_CODE_CAMERA = 123;
    private static final Integer REQUEST_CODE_FOLDER = 456;

    private static final int MY_REQUEST_CODE = 10;

    private List<Category> categories;

    private CateSetUpAdapter cateSetUpAdapter;
    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Tạo đối tượng firebase
        StorageReference storageRef = storage.getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_category);

        btnAdd = findViewById(R.id.btn_add_cate);
        edtSetUpCateName = findViewById(R.id.edt_category_name);
        edtSetUpCateDes = findViewById(R.id.edt_category_des);
        tvDeleteAllCate = findViewById(R.id.tv_delete_all_cate);
        imgCameraCate = findViewById(R.id.img_camera_set_up_cate);
        imgFolderCate = findViewById(R.id.img_folder_set_up_cate);
        imgCate = findViewById(R.id.img_set_up_cate);

        imgCameraCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        imgFolderCate.setOnClickListener(new View.OnClickListener() {
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
                Category category = new Category();
                String name = edtSetUpCateName.getText().toString().trim();
                String des = edtSetUpCateDes.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(SetUpCategoryActivity.this, "Hay nhap ten", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(des)) {
                    Toast.makeText(SetUpCategoryActivity.this, "Hay nhap mo ta", Toast.LENGTH_SHORT).show();
                    return;
                }
                category.setName(name);
                category.setDescription(des);
                if (imgCate == null) {
                    Toast.makeText(SetUpCategoryActivity.this, "Hay chon 1 anh", Toast.LENGTH_SHORT).show();
                    return;
                }
                // xử lý data ảnh
                Calendar calendar = Calendar.getInstance();

                StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");
                imgCate.setDrawingCacheEnabled(true);
                imgCate.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgCate.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(SetUpCategoryActivity.this, "error !!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        Toast.makeText(SetUpCategoryActivity.this, "success !!!", Toast.LENGTH_SHORT).show();
                        // Lấy URI của ảnh tải lên thành công
                        StorageReference storageRef = taskSnapshot.getMetadata().getReference();
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                category.setImageCateUri(uri.toString());
                                // Picasso.get().load(uri).into(imgTest);
                                AppDatabase.getInstance(SetUpCategoryActivity.this).categoryDao().insert(category);
                                Toast.makeText(SetUpCategoryActivity.this, "update success !", Toast.LENGTH_SHORT).show();

                                edtSetUpCateName.setText("");
                                edtSetUpCateDes.setText("");
                                hideKeyBoard();
                                categories = AppDatabase.getInstance(SetUpCategoryActivity.this).categoryDao().getListCategory();
                                cateSetUpAdapter.setData(categories);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SetUpCategoryActivity.this, "get image failure !!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        // xu ly hien thi rcv
        rcvSetUpCate = findViewById(R.id.rcv_set_up_cate);
        cateSetUpAdapter = new CateSetUpAdapter(new CateSetUpAdapter.IClickItemCateSetUp() {
            @Override
            public void update(Category category) {
                clickUpdate(category);

            }

            @Override
            public void delete(Category category) {
                clickDelete(category);
            }
        });
        categories = new ArrayList<>();
        cateSetUpAdapter.setData(categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvSetUpCate.setLayoutManager(linearLayoutManager);
        rcvSetUpCate.setAdapter(cateSetUpAdapter);
        loadData();

    }

    private void loadData() {
        categories = AppDatabase.getInstance(SetUpCategoryActivity.this).categoryDao().getListCategory();
        cateSetUpAdapter.setData(categories);
    }

    // Nhận hình từ cam
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgCate.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgCate.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void hideKeyBoard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void clickUpdate(Category category){
        Intent intent = new Intent(this, UpdateCateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_cate",category);
        intent.putExtras(bundle);
        startActivityForResult(intent,MY_REQUEST_CODE);
        loadData();
    }
    public void clickDelete(Category category){
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete user ? ")
                .setMessage("Are you sure!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete user
                        AppDatabase.getInstance(SetUpCategoryActivity.this).categoryDao().deleteCate(category);
                        Toast.makeText(SetUpCategoryActivity.this, "Delete success!!", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                }).setNegativeButton("No", null)
                .show();
    }
}