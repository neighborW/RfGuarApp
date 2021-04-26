package com.rifeng.p2p.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.rifeng.p2p.HomeActivity;
import com.rifeng.p2p.R;

public class LoginActivity extends BaseActivity{


    private Button siginUp;
    private Button login;
    private EditText account;
    private EditText password;
    private EditText  verification;
    private ImageView im_certification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponent();

    }

    /**
     * 初始化组件
     */
    private void initComponent(){

        siginUp = findViewById(R.id.btn_siginUp);
        login = findViewById(R.id.btn_login);
        account = findViewById(R.id.et_account);
        password = findViewById(R.id.et_password);
        verification = findViewById(R.id.et_verficate);
    }
    protected void initData() {
        siginUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击注册跳转到注册界面
                navigateTo(RegisterActivity.class);
                finish();
            }
        });
    }
}
