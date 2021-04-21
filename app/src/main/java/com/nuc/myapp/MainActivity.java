package com.nuc.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nuc.myapp.activity.BaseActivity;
import com.nuc.myapp.activity.LoginActivity;
import com.nuc.myapp.activity.RegisterActivity;

public class MainActivity extends BaseActivity {

    private Button btnLongin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //登录功能
        btnLongin = findViewById(R.id.login_btn);
        btnLongin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(LoginActivity.class);
            }
        });
        //注册界面
        btnRegister  = findViewById(R.id.register_btn);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(RegisterActivity.class);
            }
        });
    }
}
