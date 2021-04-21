package com.nuc.myapp.api;

import android.util.Log;

import com.nuc.myapp.util.AppConfig;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {

    private static OkHttpClient client;
    public static String requestUri;
    public static HashMap<String, Object> mParams;
    public static Api api = new Api();

    public Api() {
    }

    public static Api config(String uri, HashMap<String, Object> params) {
        //第一步创建OKHttpClient
        client = new OkHttpClient.Builder()
                .build();
        requestUri = AppConfig.BASE_URL + uri;
        mParams = params;

        return api;
    }

    public static void postRequsr(final TtitCallback callback) {

        //将用户名和密码换成json格式
        JSONObject jsonObject = new JSONObject(mParams);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson =
                RequestBody.create(MediaType.parse("application/json;charset=utf-8")
                        , jsonStr);
        //第三步创建Rquest
        Request request = new Request.Builder()
                .url(AppConfig.BASE_URL + "/app/login")
                .addHeader("contentType", "application/json;charset=UTF-8")
                .post(requestBodyJson)
                .build();
        //第四步创建call回调对象
        final Call call = client.newCall(request);
        //第五步发起请求
        call.enqueue(new
         Callback() {
             @Override
             public void onFailure(Call call, IOException e) {
                 Log.e("onFailure", e.getMessage());
             }

             @Override
             public void onResponse(Call call, Response response) throws IOException {
                 final String result = response.body().string();
                 callback.onSuccess(result);
             }
         });
    }
}
