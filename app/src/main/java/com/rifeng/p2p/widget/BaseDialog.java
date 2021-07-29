package com.rifeng.p2p.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rifeng.p2p.R;


/**
 * Created by keguihong on 2017/12/14.
 */

public class BaseDialog implements IDialog {
    private Dialog dialog = null;
    private View positiveButton = null;
    private View negativeButton = null;
    private LinearLayout contentLayout = null;
    private TextView titleView = null;
    private TextView messageView = null;
    private View dialogLayout = null;

    public Dialog getDialog(Activity activity) {
        if (dialog == null) {
            dialog = new Dialog(activity, R.style.DefaultDialog);

            Window window = dialog.getWindow();
            dialogLayout = activity.getLayoutInflater().inflate(R.layout.layout_dialog_default, null);
            window.setContentView(dialogLayout);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = (int) (window.getWindowManager().getDefaultDisplay().getWidth() * 0.86);
            window.setAttributes(lp);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            positiveButton = dialogLayout.findViewById(R.id.btn_dialog_dafault_positive);
            negativeButton = dialogLayout.findViewById(R.id.btn_dialog_dafault_negative);
            titleView = (TextView) dialogLayout.findViewById(R.id.text_dialog_default_title);
            messageView = (TextView) dialogLayout.findViewById(R.id.text_dialog_default_message);
            contentLayout = (LinearLayout) dialogLayout.findViewById(R.id.linearlayout_dialog_default_content);
        }
        return dialog;
    }

    public Dialog getDialog(Activity activity, View view) {
        if (dialog == null) {
            dialog = new Dialog(activity, R.style.DefaultDialog);
            Window window = dialog.getWindow();
            dialogLayout = view;
            window.setContentView(dialogLayout);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = (int) (window.getWindowManager().getDefaultDisplay().getWidth() * 0.86);
            window.setAttributes(lp);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return dialog;
    }

    @Override
    public View getView() {
        return dialogLayout;
    }

    public View getPositiveButton() {
        return positiveButton;
    }

    public View getNegativeButton() {
        return negativeButton;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getMessageView() {
        return messageView;
    }

    public void setContentView(View view) {
        if (contentLayout != null && view != null) {
            contentLayout.removeAllViews();
            contentLayout.addView(view);
        }
    }

    public View findViewById(int resId) {
        if (dialogLayout != null) {
            return dialogLayout.findViewById(resId);
        }
        return null;
    }

    @Override
    public boolean isShowing() {
        if (dialog != null) {
            dialog.isShowing();
        }
        return false;
    }

    @Override
    public void hide() {
        if (dialog != null) {
            dialog.hide();
        }
    }

    @Override
    public void setCancelable(boolean isCancelable) {
        if (dialog != null) {
            dialog.setCancelable(isCancelable);
        }
    }

    @Override
    public void setOnCancelListener(DialogInterface.OnCancelListener listener) {
        if (dialog != null) {
            dialog.setOnCancelListener(listener);
        }
    }

    @Override
    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        if (dialog != null) {
            dialog.setOnDismissListener(listener);
        }
    }

    @Override
    public void setOnKeyListener(DialogInterface.OnKeyListener listener) {
        if (dialog != null) {
            dialog.setOnKeyListener(listener);
        }
    }
}
