package com.rifeng.p2p.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

/**
 * Created by keguihong on 2017/12/14.
 */

public interface IDialog {
    View getView();

    View getPositiveButton();

    View getNegativeButton();

    TextView getTitleView();

    TextView getMessageView();

    void setContentView(View view);

    Dialog getDialog(Activity activity);

    Dialog getDialog(Activity activity, View view);

    View findViewById(int resId);

    boolean isShowing();

    void hide();

    void setCancelable(boolean isCancelable);

    void setOnCancelListener(DialogInterface.OnCancelListener listener);

    void setOnDismissListener(DialogInterface.OnDismissListener listener);

    void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener);

}
