package com.rifeng.p2p.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.activity.LoginActivity;
import com.rifeng.p2p.manager.DataManager;

public class RFAlertDialog {


    private RFAlertDialogListener mListener = null;
    private String message;
    private String leftBtnTitle;
    private String rightBtnTitle;
    private Activity mActivity;
    AlertDialog dlg;

    public interface RFAlertDialogListener{
        public abstract void leftBtnClick();
        public abstract void rightBtnClick();
    }

    public RFAlertDialog(Activity activity, String msg, String leftBtnTitle, String rightBtnTitle, RFAlertDialogListener listener){
        this.mListener = listener;
        this.leftBtnTitle = leftBtnTitle;
        this.message = msg;
        this.rightBtnTitle = rightBtnTitle;
        mActivity = activity;
    }

    public void show(){
        //创建对话框
        dlg = new AlertDialog.Builder(mActivity).create();
        dlg.show();//显示对话框
        Window window = dlg.getWindow();//获取对话框窗口
        window.setGravity(Gravity.CENTER);//对话框居中
//            window.setWindowAnimations(R.style); //设置样式
        window.setContentView(R.layout.quit_dialog_layout);
        Button rightBtn = window.findViewById(R.id.btn_determin); //获取对话框确认按钮
        rightBtn.setText(rightBtnTitle);
        Button leftBtn = window.findViewById(R.id.btn_cancel);//获取对话框取消按钮
        leftBtn.setText(leftBtnTitle);
        TextView msgTextView = window.findViewById(R.id.tv_warning);
        msgTextView.setText(message);

        ImageView iconView = window.findViewById(R.id.dialog_icon_imageview);
        iconView.setVisibility(View.GONE);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.leftBtnClick();
                }
                dlg.dismiss();//设置对话框消失
            }
        });
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mListener != null){
                    mListener.rightBtnClick();
                }
                dlg.dismiss();//设置对话框消失
            }
        });
    }

}
