package com.rifeng.p2p.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.rifeng.p2p.HomeActivity;
import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.util.LogToFileUtils;
import com.rifeng.p2p.widget.RFDialog;

import java.util.ArrayList;
import java.util.List;


public class SplashScreenActivity extends BaseActivity {
    Handler handler = new Handler();
    long delayTime = 1 * 3000;
    Class clz;
    protected boolean isIntoActivity = false;

    final protected int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 111;



    //    @BindView(R.id.btn_determin)
    Button btnSure;



    private AlertDialog dlg;

    @Override
    protected int initLayout() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initDefaultLoginClass();
        checkPermission();

    }

    private void initDefaultLoginClass() {
        clz = LoginActivity.class;
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            User user = DataManager.getInstance().getUser();
            //Agent agent = sp.getObject("Agent", Agent.class);
            if (user != null) {
                Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                //intent.putExtra("Agent", agent);
                startActivity(intent);
                finish();
            } else {
                startActivity(new Intent(SplashScreenActivity.this, clz));
                finish();
            }

        }
    };

    @Override
    protected void onStop() {
        handler.removeCallbacks(runnable);
        super.onStop();
    }

    @Override
    protected void onRestart() {
        handler.post(runnable);
        super.onRestart();
    }

    public void intoActivity() {
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }else{
            handler.postDelayed(runnable, delayTime);
        }
    }

    @SuppressLint("NewApi")
    protected void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasReadPhoneStatePermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int hasCoarseLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            int hasFineLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int hasReadStoragePermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasWriteStoragePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            final List<String> permissions = new ArrayList<String>();
            if (hasReadPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (hasReadStoragePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }


            if (!permissions.isEmpty()) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle(getString(R.string.dialog_splash_screen_permission_title));
//                builder.setMessage(R.string.dialog_splash_screen_permission_content);
//                builder.setCancelable(true);
//                builder.setPositiveButton(getString(R.string.dialog_sure),
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                SplashScreenActivity.this.requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
//                            }
//                        });
//                Dialog dialog = builder.create();
//                dialog.show();
                //创建对话框
                if(this != null && !this.isFinishing()){
                    dlg = new AlertDialog.Builder(this).create();
                    dlg.show();//显示对话框
                    Window window = dlg.getWindow();//获取对话框窗口
                    window.setGravity(Gravity.CENTER);//对话框居中
//            window.setWindowAnimations(R.style); //设置样式
                    window.setContentView(R.layout.startup_prompt_layout);
                    btnSure = window.findViewById(R.id.btn_sure); //获取对话框确认按钮
                    btnSure .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SplashScreenActivity.this.requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
                            dlg.dismiss();//设置对话框消失
                        }
                    });
                }

            } else {
                intoActivity();
                isIntoActivity = true;
                LogToFileUtils.init(this);
            }
        } else {//小于6.0
            intoActivity();
            isIntoActivity = true;
            LogToFileUtils.init(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this != null && !this.isFinishing()) {
            dlg.dismiss();
            dlg = null;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case REQUEST_CODE_SOME_FEATURES_PERMISSIONS:
                intoActivity();
                LogToFileUtils.init(this);
                isIntoActivity = true;
                break;
            default:
                break;
        }
    }

}