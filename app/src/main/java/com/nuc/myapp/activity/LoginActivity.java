package com.nuc.myapp.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.gson.Gson;
import com.nuc.myapp.R;
import com.nuc.myapp.api.Api;
import com.nuc.myapp.api.AppConfig;
import com.nuc.myapp.api.TtitCallback;
import com.nuc.myapp.entity.LoginRespone;
import com.nuc.myapp.util.StringUtil;
import java.util.HashMap;


public class LoginActivity extends BaseActivity {

    private Button btnLogin;

    private EditText etAccount;

    private EditText etPwd;
    //界面布局初始化
    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }
    //组件初始化
    @Override
    protected void initView() {
        btnLogin = findViewById(R.id.login);
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
    }
    //事件监听
    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                login(account, pwd);
            }
        });
    }

    public void login(String account, String pwd) {
        if (StringUtil.isEmpty(account)) {
            showToast("请输入账户");
            return;
        }
        if (StringUtil.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }

        HashMap<String,Object> params = new HashMap();

        params.put("mobile",account);
        params.put("password",pwd);

        Api.config(AppConfig.LOGIN,params).postRequsr(new TtitCallback() {
            //登录成功
            @Override
            public void onSuccess(final String res) {
                Log.e("onSucess",res);
                showToastSync(res);
                Gson gson = new Gson();
                LoginRespone loginRespone = gson.fromJson(res,LoginRespone.class);

                if (loginRespone.getCode() == 0){
                    String token = loginRespone.getToken();
                    saveStringToSp("token",token);//将token保存到本地
                    navigateTo(HomeActivity.class);
                    showToastSync("登录成功");
                }
                else {
                    showToastSync("登录失败");
                    navigateTo(HomeActivity.class);
                }
            }
            //登录失败
            @Override
            public void onFailure(Exception e) {
            }
        });
    }
}
