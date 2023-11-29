package com.baphan.appdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baphan.appdemo.dao.AppDatabase;
import com.baphan.appdemo.domain.User;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername,edtPassword;

    private TextView tvError;
    private Button btnLogin, btnRegister;

    private CheckBox cbRemember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        cbRemember = findViewById(R.id.cb_remember);
        tvError = findViewById(R.id.tv_error);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void handleLogin(){
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // check null
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            tvError.setText("username hoặc password không được để trống!");
            return;
        }
        User user =  AppDatabase.getInstance(this).userDao().login(username,password);
        if (user == null){
            tvError.setText("Sai tài khoản hoặc mật khẩu!");
            return;
        }
        Intent intent = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("login_user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}