package com.rifeng.p2p.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rifeng.p2p.R;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.util.StringUtils;
import com.rifeng.p2p.view.VerifyCodeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindAnim;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class VerificationActivity extends BaseActivity {

    private static final int PASSWORD_LENGTH_REQUEST = 8;

    @BindView(R.id.verify_code_view)
    VerifyCodeView verifyCodeView;
    @BindView(R.id.tv_resend)
    TextView tvResend;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.tv_back)
    TextView tvBack;
    private Bundle bundle;
    String etEmail;
    @BindView(R.id.tv_pass_tip)
    TextView tv_pass_tip;
    //初始化整个布局
    @Override
    protected int initLayout() {
        return R.layout.activity_verification;
    }
    //初始化控件
    @Override
    protected void initView() {
        etNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    tv_pass_tip.setVisibility(View.GONE);
                } else {
                    tv_pass_tip.setText("The password contains at least eight characters,including uppercase ltters, lowercase ltters, and digits");
                    if (s.toString().length() < 8) {
                        tv_pass_tip.setVisibility(View.VISIBLE);
                    } else {
                        if (StringUtils.checkPassword(s.toString())) {
                            tv_pass_tip.setVisibility(View.GONE);
                        } else {
                            tv_pass_tip.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }
    //点击事件，，请求数据等操作
    @Override
    protected void initData() {
        Intent i = getIntent();
        bundle  = i.getExtras();
        etEmail = (String) bundle.get("email");
        System.out.println("邮箱" + etEmail);
        if (etEmail != null){
            doCountdown();//开始倒计时
        }
        onClick();
    }
    //点击物理键退出到登录界面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            System.out.println("退出");
            navigateTo(LoginActivity.class);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    //点击事件
    public void onClick(){
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("Resend".equalsIgnoreCase(tvResend.getText().toString())){
                    doRequestForResenEmail();
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRequestForResetPsd();
            }
        });
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCurrentActivity();
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCurrentActivity();
            }
        });
    }
    //重新发送邮箱请求
    private void doRequestForResenEmail(){
        if(TextUtils.isEmpty(etEmail)){
            showToast(R.string.email_must_be_not_null);
            return ;
        }
        if(!etEmail.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")){
            showToast(R.string.incorrect_e_mail_formate);
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("email",etEmail);
        System.out.println("邮箱账号"+  etEmail);
        showLoadingDialog();
        mRFService.forGottenPassWords(getRequestBody(map))
                .compose(RXHelper.<String>RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        doCountdown();
                    }
                },mThrowableConsumer,mFinishAction);
    }
    //执行重置密码请求
    @SuppressLint("CheckResult")
    private void doRequestForResetPsd() {
        if (TextUtils.isEmpty(verifyCodeView.getEditContent())) {
            showToast(R.string.verfycode_must_be_not_null);
            return;
        }
        if (TextUtils.isEmpty(etNewPassword.getText().toString())) {
            showToast(R.string.pwd_must_be_not_null);
            return;
        }
        if(etNewPassword.getText().toString().length() < PASSWORD_LENGTH_REQUEST){
            showToast(R.string.pwd_format_error_length_error);
            return;
        }
        if(!etNewPassword.getText().toString().matches(".*[a-zA-Z]+.*")){
            showToast(R.string.pwd_format_error_contain_error);
            return;
        }
        if (TextUtils.isEmpty(etConfirmPassword.getText().toString())) {
            showToast(R.string.pwd_must_be_not_null);
            return;
        }
        if (!etConfirmPassword.getText().toString().equalsIgnoreCase(etConfirmPassword.getText().toString())) {
            showToast(R.string.repwd_must_be_same_with_pwd);
            return;
        }
        if (!StringUtils.checkPassword(etNewPassword.getText().toString().trim())){
            showToast("The password contains at least eight characters,including uppercase ltters, lowercase ltters, and digits");
            return;
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("captcha", verifyCodeView.getEditContent().trim());
        System.out.println("验证码" + verifyCodeView.getEditContent().trim());
        map.put("newPassword", etNewPassword.getText().toString());
        map.put("newPassword1",etConfirmPassword .getText().toString());
        map.put("email",etEmail);
        showLoadingDialog();
        mRFService.retsetPassWord(getRequestBody(map))
                .compose(RXHelper.<String>RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        showToast(R.string.pwdreset_success);
                        navigateTo(LoginActivity.class);
                        finish();
                    }
                }, mThrowableConsumer, mFinishAction);
    }
    //倒计时
    public void doCountdown(){
        /** 倒计时60秒，一次1秒 */
        CountDownTimer timer = new CountDownTimer(60*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                String newStr = ""+(millisUntilFinished/1000);
                tvResend.setText(newStr);
            }
            @Override
            public void onFinish() {
                tvResend.setText("Resend");
            }
        }.start();
    }
}
