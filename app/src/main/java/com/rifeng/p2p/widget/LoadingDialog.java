package com.rifeng.p2p.widget;

import android.app.Dialog;
import android.content.Context;

import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.rifeng.p2p.R;


/**
 * Created by caixiangyu on 2018/6/1.
 */

public class LoadingDialog extends Dialog {

    public TextView tipTextView;


    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog);
        setContentView(R.layout.dialog_loading);

        tipTextView = findViewById(R.id.tipTextView);

    }
}
