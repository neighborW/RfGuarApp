package com.rifeng.p2p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.iv_add)
    ImageView imageView;
    @BindView(R.id.iv_add)
    TextView tvAdd;

    @BindView(R.id.iv_home)
    ImageView imageView_home;
    @BindView(R.id.tv_home)
    TextView tvHome;

    @BindView(R.id.iv_test)
    ImageView im_mine;
    @BindView(R.id.vt_test)
    TextView tvMine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
