package com.rifeng.p2p.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.rifeng.p2p.BuildConfig;
import com.rifeng.p2p.HomeActivity;
import com.rifeng.p2p.R;
import com.rifeng.p2p.callback.FormResultListener;
import com.rifeng.p2p.entity.AccountInfo;
import com.rifeng.p2p.entity.User;
import com.rifeng.p2p.http.RFException;
import com.rifeng.p2p.http.RFTokenException;
import com.rifeng.p2p.http.RXHelper;
import com.rifeng.p2p.http.RetrofitFactory;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.util.StringUtil;
import com.rifeng.p2p.util.ToastUtils;
import com.uopen.cryptionkit.EncryptionManager;
import com.uopen.cryptionkit.core.AbstractCoder;

import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.functions.Func3;


public class LoginActivity extends BaseActivity {

    private static final int REQUEST_FROM_LOGIN = 0x0001;

    @BindView(R.id.text_login_signup)
    Button siginUp;
    @BindView(R.id.text_login_login)
    Button login;
    @BindView(R.id.edit_login_email)
    EditText etAccount;
    @BindView(R.id.edit_login_pwd)
    EditText etPassword;
    @BindView(R.id.edit_code_input)
    EditText verification;
    @BindView(R.id.img_verfy_code)
    ImageView im_certification;
    @BindView(R.id.text_login_password_reset)
    TextView tvForgetPassword;

//    private static EditText[] mArray;

    private String uuidKey = "";


    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        rx.Observable<CharSequence> account = RxTextView.textChanges(etAccount);
        rx.Observable<CharSequence> code = RxTextView.textChanges(verification);
        rx.Observable<CharSequence> password = RxTextView.textChanges(etPassword);

        Observable.combineLatest(account, password, code, new Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
                return !TextUtils.isEmpty(etAccount.getText().toString().trim()) && !TextUtils.isEmpty(etPassword.getText().toString().trim()) && !TextUtils.isEmpty(verification.getText().toString().trim());
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                //在这个地方可以实现是否满足过滤逻辑
                if (aBoolean) {
                    //如果满足了过滤逻辑  设置可以点击并且设置背景
                    login.setEnabled(true);
                    login.setBackgroundResource(R.drawable.shape_big_login_btn);
                } else {
                    //否则就设置背景不可点击
                    login.setEnabled(false);
                    login.setBackgroundResource(R.drawable.shape_big_login_btn_un);
                }
            }
        });
    }


    protected void initData() {
        siginUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击注册跳转到注册界面
                navigateTo(SignupActivity.class);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String account = etAccount.getText().toString().trim();
//                String password = etPassword.getText().toString().trim();
//                login(account , password);
////                navigateTo(HomeActivity.class);
////                finish();
                dologin();

            }
        });
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(VerifyEmailActivity.class);
            }
        });
        im_certification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVerifyImg();
            }
        });

        String account = DataManager.getInstance().getAccount();
        etAccount.setText(account);

        loadVerifyImg();
    }

    private void dologin() {
        if (TextUtils.isEmpty(etAccount.getText().toString())) {
            showToast(R.string.email_must_be_not_null);
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            showToast(R.string.pwd_must_be_not_null);
            return;
        }
        if (TextUtils.isEmpty(verification.getText().toString())) {
            showToast(R.string.verfycode_must_be_not_null);
            return;
        }

        verifyImg();

    }

    private void verifyImg() {
        JSONObject params = new JSONObject();
        try {
            params.put("userVerifyCodeImage", verification.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Login();

    }

    private void Login() {

        //MD5加密密码
        AbstractCoder abstractCoder = EncryptionManager.getCipher(EncryptionManager.Model.MD5);
        String md5Pwd = abstractCoder.digestSignature(etPassword.getText().toString(), null);


        RequestBody body = null;
        try {
            JSONObject obj = new JSONObject();
            obj.put("username", etAccount.getText().toString());
            obj.put("password", md5Pwd.toLowerCase());
            obj.put("captcha", verification.getText().toString());
            obj.put("captcheKey", uuidKey);
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showLoading();

        mRFService.getLoginInfo(body)
                .compose(RXHelper.RFFlowableTransformer())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        //hide();
                        //hideDialog();

                    }
                }).subscribe(new Consumer<AccountInfo>() {
            @Override
            public void accept(@NonNull AccountInfo accountInfo) throws Exception {

                // LoginActivity.this.accountInfoDataInfo = accountInfo;
                DataManager.getInstance().setAccountInfoDataInfo(accountInfo);
                int loginCount = accountInfo.getLoginCount();


                if (loginCount != 0) {
                    loginSuccess();
                } else {
//                    R.drawable.shape_dialog_orange
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    Dialog dialog = showNoticeDialog(LoginActivity.this, getString(R.string.dilog_title_pt_notice),
                            getString(R.string.read_agrement),
                            getString(R.string.dilog_btn_pt_agree), null, R.drawable.shape_dialog_orange,
                            null, false,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //去协议页面
                                    Intent i = new Intent(LoginActivity.this, ArgreeDetailActivity.class);
                                    i.putExtra("agreementType", "login");
                                    i.putExtra("type","login");
                                    startActivityForResult(i, REQUEST_FROM_LOGIN);
                                }
                            }, null);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) throws Exception {

                hideDialog();
                String msg;
                if (t instanceof IOException) {
                    msg = getString(R.string.net_error);
                } else if (t instanceof RFException) {
                    msg = t.getMessage();
                    int code = ((RFException) t).getCode();
                    if (code == 600) {
                        etAccount.setText("");

                    } else if (code == 500) {
                        verification.setText("");
                        etPassword.setText("");
                    }

                    loadVerifyImg();

                } else if (t instanceof RFTokenException) {
                    //token过期
                    msg = t.getMessage();
                    startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                    finish();
                } else {
                    if (BuildConfig.DEBUG) {
                        msg = t.getStackTrace().toString();
                    } else
                        msg = getString(R.string.unkown_error);
                }
                if (BuildConfig.DEBUG) {
                    t.printStackTrace();
                }
                hideDialog();
                if (msg != null) {
                    // 修复进去的时候白色toast
                    showToast(msg);
                }

            }
        }, mFinishAction);

    }

    protected Action mFinishAction = new Action() {
        @Override
        public void run() throws Exception {
            //hideDialog();
        }
    };

    private void loginSuccess() {
        if (DataManager.getInstance().getAccountInfoDataInfo() == null) {
            return;
        }
        hideDialog();

        DataManager.getInstance().saveAccount(etAccount.getText().toString());
        //保存账号

        User user = DataManager.getInstance().getAccountInfoDataInfo().getUser();
        DataManager.getInstance().saveToken(DataManager.getInstance().getAccountInfoDataInfo().getToken());
        //保存用户信息
        DataManager.getInstance().saveUser(user);


        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//            intent.putExtra("Agent", agents.get(0));
        startActivity(intent);
        finish();
    }


    //    private void login(String account , String pwd){
//
//        if (StringUtil.isEmpty(account)){
//            showToast("Please Input the Account" );
//            return;
//        }
//        if (StringUtil.isEmpty(pwd)){
//            showToast("Please Input the Password" );
//            return;
//        }
//        navigateTo(HomeActivity.class);
//        finish();
//    }
    //获得图片资源
    public void setImageUrl(final String url_str, Map<String, String> im) {

    }


    private void loadVerifyImg() {
        // JSONObject obj = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            params.put("width", 310);
            params.put("height", 110);
            uuidKey = UUID.randomUUID().toString();
            params.put("key", uuidKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RetrofitFactory.get(this, RetrofitFactory.URL_GET_VERIFYCODE_IMG, params, new FormResultListener() {
            @Override
            public void successResponse(Response response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] bytes = response.body().bytes();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    im_certification.setImageBitmap(bitmap);
                                }
                            });
                        } catch (IOException e) {
                            faile("LoadImg Faile");
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void faile(String request_error) {
//                hideDialog();
                ToastUtils.showToast(LoginActivity.this, request_error);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeCurrentActivity();
        }
        return super.onKeyDown(keyCode, event);
    }
}
