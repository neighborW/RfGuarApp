package com.nuc.myapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {
    private Context mComtext;
    @Override
    protected void onCreate(@Nullable Bundle sa){
        super.onCreate(sa);
        setContentView(initLayout());
        mComtext = this;
        initLayout();
        initView();
        initData();
    }
    protected abstract int initLayout();
    protected abstract void initView();
    protected abstract void initData();
    //字符串为空
    public void showToast(String str){
        Toast.makeText(mComtext,"str",Toast.LENGTH_SHORT).show();
    }
    //字符串为空
    public void showToastSync(String str){
        Looper.prepare();
        Toast.makeText(mComtext,"str",Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    //封装页面跳转
    public void navigateTo(Class activity){
        Intent intent = new Intent(mComtext,activity);
        startActivity(intent);
    }
    protected void saveStringToSp(String key,String values){
        @SuppressLint("WrongConstant")
        //将token保存到本地
        SharedPreferences sp =  getSharedPreferences("sp_ttit",MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,values);
        editor.commit();
    }
}
