package com.rifeng.p2p.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rifeng.p2p.R;

import static com.rifeng.p2p.view.RFNoticeDialog.DialogType.DialogTypeCenterBtn;
import static com.rifeng.p2p.view.RFNoticeDialog.DialogType.DialogTypeLeftRightBtn;

public class RFNoticeDialog {

    public enum DialogType{
        DialogTypeCenterBtn,
        DialogTypeLeftRightBtn,
    }


    private RFNoticeDialogListener mListener = null;
    private String message;
    private String leftBtnTitle = "";
    private String rightBtnTitle = "";
    private String centerBtnTitle = "";
    private Activity mActivity;
    AlertDialog dlg;
    private DialogType dialogType;

    public interface RFNoticeDialogListener{
        public abstract void leftBtnClick();
        public abstract void rightBtnClick();
        public abstract void centerBtnClick();
    }

    public RFNoticeDialog(Activity activity, String msg, String leftBtnTitle, String rightBtnTitle, RFNoticeDialogListener listener){
        this.mListener = listener;
        this.leftBtnTitle = leftBtnTitle;
        this.message = msg;
        this.rightBtnTitle = rightBtnTitle;
        mActivity = activity;
        dialogType = DialogTypeLeftRightBtn;
    }

    public RFNoticeDialog(Activity activity, String msg, String centerBtnTitle,  RFNoticeDialogListener listener){
        this.mListener = listener;
        this.centerBtnTitle = centerBtnTitle;
        this.message = msg;
        mActivity = activity;
        dialogType = DialogTypeCenterBtn;
    }

    public void show(){

        if (dlg == null){
            //创建对话框
            dlg = new AlertDialog.Builder(mActivity).create();
            dlg.show();//显示对话框
            Window window = dlg.getWindow();//获取对话框窗口
            window.setGravity(Gravity.CENTER);//对话框居中
            DisplayMetrics d = mActivity.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
//            window.setWindowAnimations(R.style); //设置样式
            window.setContentView(R.layout.notice_dialog_layout);
            Button rightBtn = window.findViewById(R.id.notice_btn_right); //获取对话框确认按钮
            rightBtn.setText(rightBtnTitle);
            Button leftBtn = window.findViewById(R.id.notice_btn_left);//获取对话框取消按钮
            leftBtn.setText(leftBtnTitle);
            LinearLayout leftRightBgLayout = window.findViewById(R.id.left_right_button_bg_layout);
            LinearLayout centerBgLayout = window.findViewById(R.id.center_button_bg_layout);
            Button centerBtn = window.findViewById(R.id.notice_btn_center);
            centerBtn.setText(centerBtnTitle);
            if (dialogType == DialogTypeLeftRightBtn){
                centerBgLayout.setVisibility(View.GONE);
            }else{
                leftRightBgLayout.setVisibility(View.GONE);
            }

            TextView msgTextView = window.findViewById(R.id.notice_text_message);
            msgTextView.setText(message);
            dlg.setCancelable(false);

            window.setBackgroundDrawable(new ColorDrawable(0));//去除边框


            leftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null){
                        mListener.leftBtnClick();
                    }
                    dlg.dismiss();//设置对话框消失
                    dlg = null;
                }
            });
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mListener != null){
                        mListener.rightBtnClick();
                    }
                    dlg.dismiss();//设置对话框消失
                    dlg = null;
                }
            });

            centerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        mListener.centerBtnClick();
                    }
                    dlg.dismiss();//设置对话框消失
                    dlg = null;
                }
            });



        }

    }

}
