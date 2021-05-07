package com.rifeng.p2p.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.*;

import com.rifeng.p2p.R;
import com.rifeng.p2p.api.Api;
import com.rifeng.p2p.api.ApiConfig;
import com.rifeng.p2p.api.TitleCallback;
import com.rifeng.p2p.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Response;


public class LoginActivity extends BaseActivity{

    private Button siginUp;
    private Button login;
    private EditText etAccount;
    private EditText etPassword;
    private EditText  verification;
    private ImageView im_certification;
    private TextView tvForgetPassword;

    private String uuidKey = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponent();
        initData();
    }

    /**
     * 初始化组件
     */
    private void initComponent(){
        siginUp = findViewById(R.id.btn_siginUp);
        login = findViewById(R.id.btn_login);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        verification = findViewById(R.id.et_verficate);
        tvForgetPassword = findViewById(R.id.tv_forgetPassword);
    }
    protected void initData() {
        siginUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击注册跳转到注册界面
                navigateTo(RegisterActivity.class);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String account = etAccount.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                login(account , password);

//                navigateTo(HomeActivity.class);
//                finish();
            }
        });
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(VerificationActivity.class);
                finish();
            }
        });
    }

    private void login(String account , String pwd){

        if (StringUtil.isEmpty(account)){
            showToast("Please Input the Account" );
            return;
        }
        if (StringUtil.isEmpty(pwd)){
            showToast("Please Input the Password" );
            return;
        }
        HashMap<String , Object> m = new HashMap();
        m.put("username", account);
        m.put("password", pwd);
        Api.config(ApiConfig.LOGIN,m).postRequst(new TitleCallback() {
            @Override
            public void onSuccess(String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(res);
                    }
                });
            }
            @Override
            public void onFailure(String e) {
            }
        });
//                //第一步创建OKHttpClient
//        OkHttpClient client = new OkHttpClient.Builder()
//                .build();
//        HashMap<String , Object> m = new HashMap();
//        m.put("username", account);
//        m.put("password", pwd);
//        JSONObject jsonObject = new JSONObject(m);
//        String jsonStr = jsonObject.toString();
//        RequestBody requestBodyJson =
//                RequestBody.create(MediaType.parse("application/json;charset=utf-8")
//                        , jsonStr);
//        //第三步创建Rquest
//        Request request = new Request.Builder()
//                .url(ApiConfig.BASE_URl + "/auth/appLogin")
//                .addHeader("contentType", "application/json;charset=UTF-8")
//                .post(requestBodyJson)
//                .build();
//        //第四步创建call回调对象
//        final Call call = client.newCall(request);
//        //第五步发起请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("onFailure", e.getMessage());
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String result = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast(result);
//                    }
//                });
//            }
//        });

    }
    //获得图片资源
    public void setImageUrl(final String url_str,  Map<String, String> im){

    }
}
