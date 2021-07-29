package com.rifeng.p2p.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.rifeng.p2p.R;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class VerifyEmailActivity extends BaseActivity {


    @BindView(R.id.im_back)
    ImageView imBack;

    @BindView(R.id.tv_back)
    TextView tvBack;

    @BindView(R.id.btn_continue)
    Button btnContinue;
    @BindView(R.id.et_email_verify)
    EditText etEmail;
    @Override
    protected int initLayout() {
        return R.layout.activity_verify_email;
    }
    @Override
    protected void initView() {

    }
    @Override
    protected void initData() {
        back();
        onClick();
    }
    //关闭当前Activity ，即退出这个Activity
    public void back() {
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCurrentActivity();
            }
        });
    }
    public void onClick() {

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRequest();
            }
        });
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCurrentActivity();
            }
        });
    }
    @SuppressLint("CheckResult")
    private void doRequest() {


        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            showToast(R.string.email_must_be_not_null);
            return;
        }
        if (!StringUtils.checkEmail(etEmail.getText().toString())) {
            showToast(R.string.incorrect_e_mail_formate);
            return;
        }

        RequestBody body = null;
        JSONObject obj = new JSONObject();
        try {
            obj.put("email",etEmail.getText().toString());
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        String email = etEmail.getText().toString();
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("email",email);
//        System.out.println("参数" + map);
        mRFService.forGottenPassWords(body)
                .compose(RXHelper.<String>RFFlowableTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Bundle bundle = new Bundle();
                                bundle.putString("email", etEmail.getText().toString());
                                navigateToWithBundle(VerificationActivity.class, bundle);
                            }
                        });
                    }
                }, mThrowableConsumer, mFinishAction);
    }
}
