package com.rifeng.p2p.viewmodel;


import android.app.Activity;
import android.content.Intent;

import com.rifeng.p2p.BuildConfig;
import com.rifeng.p2p.R;
import com.rifeng.p2p.activity.LoginActivity;
import com.rifeng.p2p.http.RFException;
import com.rifeng.p2p.http.RFService;
import com.rifeng.p2p.http.RFTokenException;
import com.rifeng.p2p.http.RetrofitFactory;

import java.io.IOException;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;



public class BaseViewModel {

    protected RFService mRFService  = RetrofitFactory.getInstance();

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    protected Activity currentActivity = null;

    ViewModelListener currentListener = null;

    public enum ReturnCode{
        ReturnCodeNetError, //网络错误
        ReturnCodeTokenExpire, //token 过期
        ReturnCodeOther //其他
    }


    public  interface ViewModelListener{

        public abstract void success(String msg);
        public abstract void fail(String msg, ReturnCode code);
        public abstract  void finish();

    }


    Consumer<Throwable> mThrowableConsumer = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable t) throws Exception {
            String msg = "";
            ReturnCode errorCode = ReturnCode.ReturnCodeOther;
            if (t instanceof IOException) {
                if (currentActivity != null){
                    msg = currentActivity.getString(R.string.net_error);
                }
                errorCode = ReturnCode.ReturnCodeNetError;

            } else if (t instanceof RFException) {
                msg = t.getMessage();
            }else if(t instanceof RFTokenException){
                if (currentActivity != null){
                    msg = t.getMessage();
                    currentActivity.startActivity(new Intent(currentActivity, LoginActivity.class));
                    currentActivity.finish();
                }

                errorCode = ReturnCode.ReturnCodeTokenExpire;
                //token过期

            }
            else {
                if (BuildConfig.DEBUG) {
                    msg = t.getStackTrace().toString();
                } else{
                    if (currentActivity != null){
                        msg = currentActivity.getString(R.string.unkown_error);
                    }
                }

            }
            if (BuildConfig.DEBUG) {
                t.printStackTrace();
            }

            if (currentListener != null){
                currentListener.fail(msg, errorCode);
            }


        }
    };

    protected Action mFinishAction = new Action() {
        @Override
        public void run() throws Exception {

            if (currentListener != null){
                currentListener.finish();
            }
        }
    };
}
