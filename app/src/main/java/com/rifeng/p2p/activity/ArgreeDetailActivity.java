package com.rifeng.p2p.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.UserAgreement;
import com.rifeng.p2p.http.RXHelper;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class ArgreeDetailActivity extends BaseActivity {

    @BindView(R.id.iv_base_back)
    ImageView imBack;
    @BindView(R.id.text_toolbar_title)
    TextView textToolbarTitle;
    @BindView(R.id.tv_agreet_detail)
    TextView tvAgreetDetail;
    private String agreementType;
    @BindView(R.id.btn_agreet)
    Button btn_agreet;
    @BindView(R.id.ll_agreet)
    LinearLayout ll_agreet;

    @Override
    protected int initLayout() {
        return R.layout.activity_argree_detail;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        textToolbarTitle.setText("Registration Agreement");
        onClick();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle.get("agreementType") != null) {
            System.out.println("不为空:" + bundle.get("agreementType"));
            agreementType = intent.getStringExtra("agreementType");

            if (agreementType.equals("login")) {
                if (intent.getStringExtra("type") != null && intent.getStringExtra("type").equals("login")) {
                    ll_agreet.setVisibility(View.VISIBLE);
                }
            }
        }
        getAgreeContent();
    }

    //回退
    public void onClick() {
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCurrentActivity();
            }
        });
        btn_agreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAgree();
            }
        });
    }

    /**
     * 同意协议
     */
    private void setAgree() {
        mRFService.getAgreeAgrement()
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NotNull String s) throws Exception {
//                        if (loginAgreementBean.getCode() == 200 && loginAgreementBean.isOk()) {
//                            finish();
//                        } else {
//                            ToastUtils.showToast(ArgreeDetailActivity.this, loginAgreementBean.getMsg());
//                        }
                        finish();
//                        Log.e("HttpLog", s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable t) throws Exception {
                        String msg = t.getMessage();
                        showToast(msg);
                    }
                }, mFinishAction);
    }

    //点击物理键退出到注册界面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("退出");
            removeCurrentActivity();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    //获取协议内容
    private void getAgreeContent() {
        mRFService.getAgreementContent(agreementType)
                .compose(RXHelper.<UserAgreement>RFFlowableTransformer())
                .subscribe(new Consumer<UserAgreement>() {
                    @Override
                    public void accept(UserAgreement s) throws Exception {

                        Log.i("=====return agreement:", s.getContext());

                        String title = s.getTitle();
                        String agreementDesc = s.getContext();
                        System.out.println("获取内容" + agreementDesc);
                        textToolbarTitle.setText(title);
                        tvAgreetDetail.setText(Html.fromHtml(agreementDesc));

                    }
                }, mThrowableConsumer, mFinishAction);
    }
}
