package com.rifeng.p2p.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.activity.AboutMeActivity;
import com.rifeng.p2p.activity.ArgreeDetailActivity;
import com.rifeng.p2p.activity.LoginActivity;
import com.rifeng.p2p.activity.PasswordResetActivity;
import com.rifeng.p2p.manager.DataManager;

public class MineFragment extends BaseFragment {


    private RelativeLayout aboutMe , changePassword,readAgreemen,logOut;

    //初始化布局
    @Override
    protected int initLayout() {
        return R.layout.fragment_mine;
    }


    Button btnCancel;
    //    @BindView(R.id.btn_determin)
    Button btnDetermin;

    TextView tvWarning;

    private AlertDialog dlg;
    //初始化组件
    @Override
    protected void initView() {
        aboutMe = mRootView.findViewById(R.id.rl_about_me);
        changePassword = mRootView.findViewById(R.id.rl_change);
        readAgreemen = mRootView.findViewById(R.id.rl_read_agree);
        logOut = mRootView.findViewById(R.id.rl_logout);
    }
    //触发事件
    @Override
    protected void initData() {
        //关于我
        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(AboutMeActivity.class);
            }
        });
        //点击修改密码
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(PasswordResetActivity.class);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        readAgreemen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("agreementType" ,"login");
                navigateToWithBundle(ArgreeDetailActivity.class , bundle);
            }
        });
    }

    public void openDialog(){

        //创建对话框
        dlg = new AlertDialog.Builder(getActivity()).create();
        dlg.show();//显示对话框
        Window window = dlg.getWindow();//获取对话框窗口
        window.setGravity(Gravity.CENTER);//对话框居中
//            window.setWindowAnimations(R.style); //设置样式
        window.setContentView(R.layout.quit_dialog_layout);
        btnDetermin = window.findViewById(R.id.btn_determin); //获取对话框确认按钮
        btnCancel = window.findViewById(R.id.btn_cancel);//获取对话框取消按钮
        tvWarning = window.findViewById(R.id.tv_warning);
        tvWarning.setText("You will log out of your current account. Are you sure to log out?");
        btnCancel .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();//设置对话框消失
            }
        });
        btnDetermin .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataManager.getInstance().deleteUser();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                dlg.dismiss();//设置对话框消失
            }
        });
    }
}
