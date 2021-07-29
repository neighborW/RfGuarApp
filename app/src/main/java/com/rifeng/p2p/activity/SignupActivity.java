package com.rifeng.p2p.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.InvitationCode;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func3;
import rx.functions.Func4;

public class SignupActivity extends BaseActivity {

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_invitation_code)
    EditText etInvitationCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.tv_pass_tip)
    TextView tv_pass_tip;
    @BindView(R.id.tv_agreet_context)
    TextView tvAgreetContext;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    @BindView(R.id.cb_agree_checkbox)
    CheckBox cbAgreeCheckbox;

    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.tv_back)
//    boolean isSelectOrNot = false;
    TextView tvBack;


    private static boolean isReadedOrNot = false;

    boolean isVaildOrNot = false; //验证码是否有效
    private String companyName;


    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
            }
        }
    };

    //初始化布局
    @Override
    protected int initLayout() {
        return R.layout.activity_signup;
    }

    //初始化控件
    @Override
    protected void initView() {
        rx.Observable<CharSequence> email = RxTextView.textChanges(etEmail);
        rx.Observable<CharSequence> code = RxTextView.textChanges(etInvitationCode);
        rx.Observable<CharSequence> password = RxTextView.textChanges(etPassword);
        rx.Observable<CharSequence> newpassword = RxTextView.textChanges(etNewPassword);

        Observable.combineLatest(email, password, code, newpassword, new Func4<CharSequence, CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) {
                return !TextUtils.isEmpty(etEmail.getText().toString().trim()) && !TextUtils.isEmpty(etPassword.getText().toString().trim()) && !TextUtils.isEmpty(etNewPassword.getText().toString().trim())&& !TextUtils.isEmpty(etInvitationCode.getText().toString().trim());
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                //在这个地方可以实现是否满足过滤逻辑
                if (aBoolean) {
                    //如果满足了过滤逻辑  设置可以点击并且设置背景
                    btnContinue.setEnabled(true);
                    btnContinue.setBackgroundResource(R.drawable.sign_up_button);
                } else {
                    //否则就设置背景不可点击
                    btnContinue.setEnabled(false);
                    btnContinue.setBackgroundResource(R.drawable.sign_up_button_un);
                }
            }
        });
    }

    //初始化点击事件，请求数据等操作
    @Override
    protected void initData() {
        back(); //返回
        chekPassword();//检测密码
        onClick(); //点击继续
    }

    private void chekPassword() {
        etPassword.addTextChangedListener(new TextWatcher() {
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

    //点击物理键退出到注册界面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("退出");
            navigateTo(LoginActivity.class);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    //返回
    public void back() {
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(LoginActivity.class);
                finish();
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(LoginActivity.class);
                finish();
            }
        });
    }

    //所有点击事件
    public void onClick() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheckRegister();
            }
        });
        cbAgreeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClipData.Item item = (ClipData.Item) buttonView.getTag();
                if (isChecked) {
                    isReadedOrNot = true;
                    cbAgreeCheckbox.setBackgroundResource(R.mipmap.select_checkbox);
                } else {
                    isReadedOrNot = false;
                    cbAgreeCheckbox.setBackgroundResource(R.mipmap.checkbox_image);
                }
            }
        });
        tvAgreetContext.setOnClickListener(new View.OnClickListener() { //阅读协议，并且设置成已读
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("agreementType", "register");
                navigateToWithBundle(ArgreeDetailActivity.class, bundle);
            }
        });
    }

    public void doCheckRegister() {
        if (checkForm()) {
            checkRegister();
        }
    }

    //表单校验
    public boolean checkForm() {
        boolean isCheckForm = true;
        if (!isReadedOrNot) {
            isCheckForm = false;
            showToast(R.string.agreement_unread_error);
        }
        return isCheckForm;
    }

    public void checkRegister() {
        if (StringUtils.isEmpty(etEmail.getText().toString().trim())) {
            showToast("Please input email");
            return;
        }
        if (StringUtils.isEmpty(etInvitationCode.getText().toString().trim())) {
            showToast("Please input invitation code");
            return;
        }
        if (StringUtils.isEmpty(etPassword.getText().toString().trim())) {
            showToast("Please input Password");
            return;
        }
        if (StringUtils.isEmpty(etNewPassword.getText().toString().trim())) {
            showToast("Please input New Password");
            return;
        }

        if (!StringUtils.checkPassword(etPassword.getText().toString().trim())) {
            showToast("The password contains at least eight characters,including uppercase ltters, lowercase ltters, and digits");
            return;
        }
        RequestBody body = null;
        try {
            JSONObject dataObject = new JSONObject();
            dataObject.put("email", etEmail.getText().toString());
            dataObject.put("invitationCode", etInvitationCode.getText().toString());
            dataObject.put("password", etPassword.getText().toString());
            dataObject.put("confirmPassword", etNewPassword.getText().toString());
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), dataObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mRFService.getInvitationCode(body)
                .compose(RXHelper.RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String jsonObject) throws Exception {
                        Bundle bundle = new Bundle();
                        bundle.putString("email", etEmail.getText().toString());
                        bundle.putString("invitationCode", etInvitationCode.getText().toString());
                        bundle.putString("password", etPassword.getText().toString());
                        bundle.putString("confirmPassword", etNewPassword.getText().toString());
//                        bundle.putString("companyName", companyName);
//                        System.out.println();
//                        if (companyName != null) {
                        navigateToWithBundle(RegisterActivity.class, bundle);
                        finish();
//                        } else if (companyName == null) {
//                            showToast("The code is invaild");
//                        }
                    }
                }, mThrowableConsumer, mFinishAction);
    }
}
