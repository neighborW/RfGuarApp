package com.rifeng.p2p.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.rifeng.p2p.R;
import com.rifeng.p2p.http.RetrofitFactory;
import com.rifeng.p2p.manager.DataManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by caixiangyu on 2018/6/27.
 */

public class NewReportActivity extends BaseActivity {

    @BindView(R.id.wvData)
    WebView mWvData;

    @BindView(R.id.iv_base_back)
    ImageView imBack;

    private String testId;
    @Override
    protected int initLayout() {
        return R.layout.activity_new_report;
    }

    @Override
    protected void initView() {
        initWebView();
    }

    @Override
    protected void initData() {


        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        testId = bundle.get("testId").toString();
        testId = getIntent().getStringExtra("testId");
        String url = RetrofitFactory.REPORT_URL + "?testId=" + testId + "&token="+ DataManager.getInstance().getToken();
        WebSettings settings = mWvData.getSettings();
        settings.setDomStorageEnabled(true);//开启DOM
        mWvData.loadUrl(url);
        onClick();
    }


    public void onClick(){
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCurrentActivity();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        testId = getIntent().getStringExtra("testId");
        System.out.println("报告TestId" + testId);
        String url = RetrofitFactory.REPORT_URL + "?testId=" + testId + "&token="+ DataManager.getInstance().getToken();
        mWvData.loadUrl(url);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    private void initWebView() {
        // 设置与Js交互的权限
        WebSettings webSettings = mWvData.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBlockNetworkLoads(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
    }
}
