package com.rifeng.p2p.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.rifeng.p2p.activity.BaseActivity;
import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.http.RFException;
import com.rifeng.p2p.http.RFService;
import com.rifeng.p2p.http.RetrofitFactory;
import com.rifeng.p2p.widget.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    private Unbinder unbinder;
    LoadingDialog mDialog;

    RFService mRFService = RetrofitFactory.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(initLayout(), container, false);
            initView();
        }
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //初始化布局
    protected abstract int initLayout();
    //初始化控件
    protected abstract void initView();
    //初始化数据
    protected abstract void initData();

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    public void showToastSync(String msg) {
        Looper.prepare();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    public void navigateTo(Class cls) {
        Intent in = new Intent(getActivity(), cls);
        startActivity(in);
    }
    public void navigateToWithBundle(Class cls, Bundle bundle) {
        Intent in = new Intent(getActivity(), cls);
        in.putExtras(bundle);
        startActivity(in);
    }
    public void navigateToWithFlag(Class cls, int flags) {
        Intent in = new Intent(getActivity(), cls);
        in.setFlags(flags);
        startActivity(in);
    }
    protected void insertVal(String key, String val) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }
    protected String findByKey(String key) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        return sp.getString(key, "");
    }
    protected void removeByKey(String key) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.commit();
    }
    public void showToast(int msgId) {
        Toast.makeText(BaseApp.getInstance(), getString(msgId), Toast.LENGTH_SHORT).show();
    }
    public void showLoadingDialog() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showLoading();
        }
    }
    public RequestBody getRequestBody(Map<String, String> map){
        RequestBody body = null;
        try {
            JSONObject obj = new JSONObject();
            map.keySet();

            for(Map.Entry<String, String> entry : map.entrySet()){
                obj.put(entry.getKey(),entry.getValue());
            }
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body;
    }
    Consumer<Throwable> mThrowableConsumer = new Consumer<Throwable>() {
        @Override
        public void accept(Throwable t) throws Exception {
            String msg;
            if (t instanceof IOException) {
                msg = getString(R.string.net_error);
            } else if (t instanceof RFException) {
                msg = t.getMessage();
            } else {
                if (BuildConfig.DEBUG) {
                    msg = t.getStackTrace().toString();
                } else
                    msg = getString(R.string.unkown_error);
            }
            if (BuildConfig.DEBUG) {
                t.printStackTrace();
            }
            hideLoadingDialog();
            showToast(msg);
        }
    };
    public void hideLoadingDialog() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).hideDialog();
        }
    }

    public void hideDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
    protected Action mFinishAction = new Action() {
        @Override
        public void run() throws Exception {
            hideDialog();
        }
    };


}
