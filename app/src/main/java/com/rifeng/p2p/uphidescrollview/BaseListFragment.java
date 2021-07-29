package com.rifeng.p2p.uphidescrollview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rifeng.p2p.BuildConfig;
import com.rifeng.p2p.R;
import com.rifeng.p2p.activity.LoginActivity;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.http.RFException;
import com.rifeng.p2p.http.RFTokenException;
import com.rifeng.p2p.widget.LoadingDialog;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.io.IOException;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by caizhiming on 2016/11/14.
 */
public abstract class BaseListFragment extends Fragment {

    protected Context mContext;

//    RFProgressDialog mProgressDialog;
    protected abstract View getSlidableView();

    LoadingDialog mDialog;


    public void showToastSync(String msg) {
        Looper.prepare();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void navigateTo(Class cls) {
        Intent in = new Intent(getActivity(), cls);
        startActivity(in);
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
                startActivity(new Intent(getContext(), LoginActivity.class));

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

    protected Action mFinishAction = new Action() {
        @Override
        public void run() throws Exception {
            hideDialog();
        }
    };

//    protected void hide() {
//        if (mProgressDialog != null) {
//            mProgressDialog.dismiss();
//        }
//    }

    public void hideDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(BaseApp.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public void navigateToWithBundle(Class cls, Bundle bundle) {
        Intent in = new Intent(getActivity(), cls);
        in.putExtras(bundle);
        startActivity(in);
    }
    public void onLoadMore(final SmartRefreshLayout layout){
        // 模拟加载5秒钟
        layout.finishLoadMore(3000);
    }

    public void refresh(final SmartRefreshLayout layout){
        //刷新
        layout.finishRefresh(3000);
    }
}
