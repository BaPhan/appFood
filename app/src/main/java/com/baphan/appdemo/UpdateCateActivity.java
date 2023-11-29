package com.baphan.appdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import android.widget.Toast;

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
import java.util.Calendar;

public class UpdateCateActivity extends AppCompatActivity {

    private EditText edtName, edtDes;

    private ImageButton imgCameraCate, imgFolderCate;
    private ImageView imgCate, imgTest;
    private Button btnUpdate;
    private Category category;

    private static final Integer REQUEST_CODE_CAMERA = 123;
    private static final Integer REQUEST_CODE_FOLDER = 456;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Tạo đối tượng firebase
        StorageReference storageRef = storage.getReference();
        setContentView(R.layout.activity_update_cate);

        edtName = findViewById(R.id.edt_category_update_name);
        edtDes = findViewById(R.id.edt_category_update_des);
        btnUpdate = findViewById(R.id.btn_update_cate);
        imgCameraCate = findViewById(R.id.img_camera_update_cate);
        imgCate = findViewById(R.id.img_update_cate);
        imgFolderCate = findViewById(R.id.img_folder_update_cate);

        category = (Category) getIntent().getExtras().get("obj_cate");
        if (category != null){
            edtName.setText(category.getName());
            edtDes.setText(category.getDescription());
            Picasso.get().load(category.getImageCateUri()).into(imgCate);
        }
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
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String des = edtDes.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(UpdateCateActivity.this, "Hay nhap ten", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(des)) {
                    Toast.makeText(UpdateCateActivity.this, "Hay nhap mo ta", Toast.LENGTH_SHORT).show();
                    return;
                }
                category.setName(name);
                category.setDescription(des);
                if (imgCate == null) {
                    Toast.makeText(UpdateCateActivity.this, "Hay chon 1 anh", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(UpdateCateActivity.this, "error !!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        Toast.makeText(UpdateCateActivity.this, "success !!!", Toast.LENGTH_SHORT).show();
                        // Lấy URI của ảnh tải lên thành công
                        StorageReference storageRef = taskSnapshot.getMetadata().getReference();
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                category.setImageCateUri(uri.toString());
                                // Picasso.get().load(uri).into(imgTest);
                                AppDatabase.getInstance(UpdateCateActivity.this).categoryDao().updateCategory(category);
                                Toast.makeText(UpdateCateActivity.this, "update success !", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent();
                                setResult(Activity.RESULT_OK,intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateCateActivity.this, "get image failure !!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

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
}