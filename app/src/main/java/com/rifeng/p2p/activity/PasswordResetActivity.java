package com.rifeng.p2p.activity;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.rifeng.p2p.R;
import com.rifeng.p2p.fragment.MineFragment;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class PasswordResetActivity extends BaseActivity {

    private EditText currentPassword , newPassword , confirmPassword;
    private ImageView ivBack;
    private Button btnSubmit;
    private static final int PASSWORD_LENGTH_REQUEST = 8;
    private TextView textToolbarTitle;
    private TextView tv_pass_tip;
    //初始化布局
    @Override
    protected int initLayout() {
        return R.layout.activity_password_reset;
    }
    //初始化控件
    @Override
    protected void initView() {
        btnSubmit = findViewById(R.id.btn_submit);
        ivBack = findViewById(R.id.iv_base_back);
        currentPassword = findViewById(R.id.et_current_password);
        newPassword = findViewById(R.id.et_new_password);
        confirmPassword = findViewById(R.id.et_confirm_password);
        textToolbarTitle = findViewById(R.id.text_toolbar_title);
        tv_pass_tip = findViewById(R.id.tv_pass_tip);

        newPassword.addTextChangedListener(new TextWatcher() {
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
    //请求数据，点击事件等
    @Override
    protected void initData() {
        textToolbarTitle.setText("Password Reset");
        back();
        setBtnSubmit();
    }

    private void setBtnSubmit(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRequest();
            }
        });
    }
    private void back(){
        //退出回到原界面
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCurrentActivity();
            }
        });
    }
    @SuppressLint("CheckResult")
    private void doRequest() {


        if (TextUtils.isEmpty(currentPassword.getText().toString())) {
            showToast(R.string.pwd_must_be_not_null);
            return;
        }
        if (TextUtils.isEmpty(newPassword.getText().toString())) {
            showToast(R.string.pwd_must_be_not_null);
            return;
        }

        if(newPassword.getText().toString().length() < PASSWORD_LENGTH_REQUEST){
            showToast(R.string.pwd_format_error_length_error);
            return;
        }
        if(!newPassword.getText().toString().matches(".*[a-zA-Z]+.*")){
            showToast(R.string.pwd_format_error_contain_error);
            return;
        }

        if (TextUtils.isEmpty(newPassword.getText().toString())) {
            showToast(R.string.pwd_must_be_not_null);
            return;
        }

        if (!confirmPassword.getText().toString().equalsIgnoreCase(newPassword.getText().toString())) {
            showToast(R.string.repwd_must_be_same_with_pwd);
            return;
        }
        if (!StringUtils.checkPassword(newPassword.getText().toString().trim())){
            showToast("The password contains at least eight characters,including uppercase ltters, lowercase ltters, and digits");
            return;
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("newPassword", newPassword.getText().toString());
        map.put("newPassword1", confirmPassword.getText().toString());
        map.put("oldPassword" , currentPassword.getText().toString());
        showLoadingDialog();
        mRFService.modifiedPassWord(getRequestBody(map))
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        showToast("Operation Succussful");
                    }
                }, mThrowableConsumer, mFinishAction);
    }
}
