package com.rifeng.p2p.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class CustomDialog  extends Dialog {


    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
