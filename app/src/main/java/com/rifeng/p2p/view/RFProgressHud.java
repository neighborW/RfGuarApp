package com.rifeng.p2p.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.activity.LoginActivity;
import com.rifeng.p2p.activity.PressureTestActivity;
import com.rifeng.p2p.util.DisplayUtil;
import com.rifeng.p2p.widget.LoadingDialog;

import java.util.Timer;
import java.util.TimerTask;

public class RFProgressHud {

    private static Activity currentActivity;

    private static LoadingDialog loadingDialog = null;

    public interface RFProgressHudListener{
        public void didCloseHub();
    }

    public static void showSuccessMsg(Activity activity, String msg){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showSuccessMsg(activity, msg, null);
            }
        });

    }
    public static void showSuccessMsg(Activity activity, String msg, RFProgressHudListener listener){


        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //创建对话框
                AlertDialog dlg = new AlertDialog.Builder(activity).create();
                dlg.show();//显示对话框
                Window window = dlg.getWindow();//获取对话框窗口
                window.setGravity(Gravity.CENTER);//对话框居中
//            window.setWindowAnimations(R.style); //设置样式
                int dialogWidth = DisplayUtil.dip2px2(activity, 205);
                int dialogHeight = DisplayUtil.dip2px2(activity, 205);;
                dlg.getWindow().setLayout(dialogWidth, dialogHeight);
                window.setContentView(R.layout.layout_custom_dialog);
                window.setBackgroundDrawableResource(R.drawable.shape_tip_dialog);
                TextView tvPrompt = window.findViewById(R.id.id_prompt);
                ImageView iconView = window.findViewById(R.id.dialog_status_icon);
                iconView.setImageResource(R.mipmap.register_success);
                tvPrompt.setText(msg);
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        dlg.dismiss();
                        t.cancel();
                        if (listener != null){
                            listener.didCloseHub();
                        }
                    }
                }, 2000);
            }
        });


    }

    public static void showErrorMsg(Activity activity, String msg){

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //创建对话框
                AlertDialog dlg = new AlertDialog.Builder(activity).create();
                dlg.show();//显示对话框
                Window window = dlg.getWindow();//获取对话框窗口
                window.setGravity(Gravity.CENTER);//对话框居中
                int dialogWidth = DisplayUtil.dip2px2(activity, 205);
                int dialogHeight = DisplayUtil.dip2px2(activity, 205);;
                dlg.getWindow().setLayout(dialogWidth, dialogHeight);
//            window.setWindowAnimations(R.style); //设置样式
                window.setContentView(R.layout.layout_custom_dialog);
                TextView tvPrompt = window.findViewById(R.id.id_prompt);
                ImageView iconView = window.findViewById(R.id.dialog_status_icon);
                iconView.setImageResource(R.mipmap.test_fail);
                tvPrompt.setText(msg);
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        dlg.dismiss();
                        t.cancel();
                    }
                }, 2000);
            }
        });


    }


    public static void showLoading(Activity context, String msg) {

        currentActivity = context;
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(context);
                }
                if (!loadingDialog.isShowing()) {
                    loadingDialog.tipTextView.setText(msg);
                    loadingDialog.show();

                    Window dialogWindow = loadingDialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
                    lp.width = d.widthPixels;
                    lp.height = d.heightPixels;
                    dialogWindow.setAttributes(lp);
                }

            }
        });


    }


    public static void hideDialog() {
        if (currentActivity != null){
            currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                        loadingDialog = null;
                    }
                }
            });
        }



    }
}
