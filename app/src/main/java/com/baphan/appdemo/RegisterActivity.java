package com.baphan.appdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baphan.appdemo.dao.AppDatabase;
import com.baphan.appdemo.domain.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername,edtPassword,edtFullname,edtEmail,edtAddress;
    private Button btnRegister,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUsername = findViewById(R.id.edt_username);
        edtAddress = findViewById(R.id.edt_address);
        edtPassword  = findViewById(R.id.edt_password);
        edtFullname = findViewById(R.id.edt_fullname);
        edtEmail = findViewById(R.id.edt_email);
         btnRegister = findViewById(R.id.btn_register);
         btnBack = findViewById(R.id.btn_back);


         btnBack.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });

         btnRegister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 handleRegister();
             }
         });
    }
    private void handleRegister(){
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String fullName = edtFullname.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        if (TextUtils.isEmpty(username)){
            edtUsername.setError("username không được để trống");
            return;
        }
        if (TextUtils.isEmpty(password)){
            edtPassword.setError("password không được để trống");
            return;
        }
        if (TextUtils.isEmpty(fullName)){
            edtFullname.setError("fullName không được để trống");
            return;
        }
        if (TextUtils.isEmpty(email)){
            edtEmail.setError("email không được để trống");
            return;
        }
        if (TextUtils.isEmpty(address)){
            edtAddress.setError("address không được để trống");
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setAddress(address);
        user.setFullName(fullName);
        if (isUserExist(user)){
            Toast.makeText(this,"User exist",Toast.LENGTH_SHORT).show();
            return;
        }
        AppDatabase.getInstance(this).userDao().insertUser(user);
        Toast.makeText(this,"Tạo tài khoản thành công",Toast.LENGTH_SHORT).show();
        edtUsername.setText("");
        edtAddress.setText("");
        edtFullname.setText("");
        edtEmail.setText("");
        edtPassword.setText("");
        hideKeyBoard();
        finish();
    }
    public void hideKeyBoard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    private boolean isUserExist(@NotNull User user) {
        List<User> list = AppDatabase.getInstance(this).userDao().checkUser(user.getUsername());
        return list != null && !list.isEmpty();
    }
}