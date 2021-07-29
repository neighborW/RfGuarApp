package com.rifeng.p2p.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import com.rifeng.p2p.BuildConfig;
import com.rifeng.p2p.R;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.http.RFException;
import com.rifeng.p2p.http.RFService;
import com.rifeng.p2p.http.RFTokenException;
import com.rifeng.p2p.http.RetrofitFactory;
import com.rifeng.p2p.util.*;
import com.rifeng.p2p.widget.LoadingDialog;
import com.rifeng.p2p.widget.RFDialog;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.rifeng.p2p.util.UIUtils.getContext;
import io.reactivex.functions.Action;

public abstract class BaseActivity extends AppCompatActivity {


    protected RFService mRFService;
    LoadingDialog mDialog;
    private Context mContext;

    protected Bundle saveInstan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DisplayUtil.setCustomDensity(this, getApplication());
        super.onCreate(savedInstanceState);
        mContext = this;
        saveInstan =  savedInstanceState;
        setContentView(initLayout());
        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        mRFService = RetrofitFactory.getInstance();
        //将当前的activity添加到ActivityManager中
        ActivityUtil.getInstance().addActivity(this);
        initView();
        initData();
    }
    protected abstract int initLayout();
    protected abstract void initView();
    protected abstract void initData();
    //跳转到cls
    public void  navigateTo(Class cls) {
        Intent in = new Intent(mContext, cls);
        startActivity(in);
    }
    public void navigateToWithBundle(Class cls, Bundle bundle) {
        Intent in = new Intent(mContext, cls);
        in.putExtras(bundle);
        startActivity(in);
    }
    public void showToast(String msg){
        Toast.makeText(mContext ,msg,Toast.LENGTH_SHORT ).show();
    }
    public void showToast(int msgId) {
        Toast.makeText(BaseApp.getInstance(), getString(msgId), Toast.LENGTH_SHORT).show();
    }

    public void showLoading() {
        if (mDialog == null) {
            mDialog = new LoadingDialog(this);
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }


    public void hideDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    protected Dialog showNoticeDialog(Context context, String title, String message, String btnPositiveStr, String btnNegativeStr, int backgroundRes, String textColor, boolean cancelable, View.OnClickListener btnPositivelistener, View.OnClickListener btnNegativelistener) {
        Dialog dialog = null;
        try {
            RFDialog.Builder builder = new RFDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(btnPositiveStr, btnPositivelistener).setNegativeButton(btnNegativeStr, btnNegativelistener);
            builder.setCancelable(cancelable);
            dialog = builder.create();

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) builder.getIDialog().getTitleView().getLayoutParams();
            params.gravity = Gravity.LEFT;
            builder.getIDialog().getTitleView().setGravity(Gravity.LEFT);
            if (backgroundRes != 0) {
                builder.getIDialog().getView().setBackgroundResource(backgroundRes);
            }
            builder.getIDialog().getMessageView().setMovementMethod(ScrollingMovementMethod.getInstance());
            builder.getIDialog().getMessageView().setGravity(Gravity.LEFT);
            if (!StringUtils.isNullOrEmpty(textColor)) {
                builder.getIDialog().getTitleView().setTextColor(Color.parseColor(textColor));
                builder.getIDialog().getMessageView().setTextColor(Color.parseColor(textColor));
            }

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    View button = builder.getIDialog().getPositiveButton();
                    button.setBackgroundResource(R.drawable.sign_up_button);
                }
            });

            dialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }

        return dialog;
    }

    protected void insertVal(String key, String val) {
        SharedPreferences sp = getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }
    protected Action mFinishAction = new Action() {
        @Override
        public void run() throws Exception {
            hideDialog();
        }
    };



    //删除当前的Activity
    public void removeCurrentActivity(){
        ActivityUtil.getInstance().finishActivity(this);
    }
    //销毁所有的activity
    public void removeAll(){
        ActivityUtil.getInstance().finishAllActivity();
    }


    public Consumer<Throwable> mThrowableConsumer = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable t) throws Exception {
            hideDialog();
            String msg;
            if (t instanceof IOException) {
                msg = getString(R.string.net_error);
            } else if (t instanceof RFException) {
                msg = t.getMessage();
            }else if(t instanceof RFTokenException){
                //token过期
                msg = t.getMessage();
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                BaseActivity.this.finish();
            }
            else {
                if (BuildConfig.DEBUG) {
                    msg = t.getStackTrace().toString();
                } else
                    msg = getString(R.string.unkown_error);
            }
            if (BuildConfig.DEBUG) {
                t.printStackTrace();
            }
            hideDialog();
            if(msg != null){
                // 修复进去的时候白色toast
                showToast(msg);
            }

        }
    };

    public RequestBody getRequestBody(Map<String, String> map){
        RequestBody body = null;
        try {
            JSONObject obj = new JSONObject();
            map.keySet();
            for(Map.Entry<String, String> entry : map.entrySet()){
                obj.put(entry.getKey(),entry.getValue());
            }
            System.out.println("json参数" + obj);
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return body;
    }

    public void showLoadingDialog() {
        if (this != null) {
            this.showLoading();
        }
    }

}
