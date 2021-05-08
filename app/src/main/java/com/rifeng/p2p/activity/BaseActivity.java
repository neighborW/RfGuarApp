package com.rifeng.p2p.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rifeng.p2p.util.StringUtil;

public class BaseActivity extends AppCompatActivity{

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }
    //跳转到cls
    public void  navigateTo(Class cls)
    {
        Intent in = new Intent(mContext, cls);
        startActivity(in);
    }

    public void showToast(String msg){
        Toast.makeText(mContext ,msg,Toast.LENGTH_SHORT ).show();
    }
}
