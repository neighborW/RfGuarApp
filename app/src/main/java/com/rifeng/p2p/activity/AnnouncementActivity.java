package com.rifeng.p2p.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rifeng.p2p.R;

import butterknife.BindView;

public class AnnouncementActivity extends BaseActivity {

    private TextView tvTitle ,tvDate ;
    private WebView tvContext;
    private ImageView imBack;

    @BindView(R.id.text_toolbar_title)
    TextView tvToolbarTitle;




    @Override
    protected int initLayout() {
        return R.layout.activity_announcement;
    }
    @Override
    protected void initView() {

        tvToolbarTitle.setText("Announcement");
        tvTitle = findViewById(R.id.tv_new_title);
        tvDate = findViewById(R.id.tv_date);
        tvContext = findViewById(R.id.tv_context);
        imBack = findViewById(R.id.iv_base_back);
    }
    @Override
    protected void initData() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tvTitle.setText(bundle.get("title").toString());
        tvDate.setText(bundle.get("startTime").toString());

        tvContext.loadDataWithBaseURL(null , bundle.get("content").toString() , "text/html" , "utf-8" , null);
        back();
    }

    private void back(){
        //退出回到原界面
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCurrentActivity();
            }
        });
    }
}
