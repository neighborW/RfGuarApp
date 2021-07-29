package com.rifeng.p2p.http;

import android.util.Log;


import com.rifeng.p2p.BuildConfig;
import com.rifeng.p2p.util.LogToFileUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by caixiangyu on 2018/6/20.
 */

public class HttpApi {
    private static final long TIMEOUT = 30;
    private static HttpApi sHttpApi;

    private HttpApi() {
        mClient = new OkHttpClient.Builder()
                // 添加通用的Header
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        return chain.proceed(builder.build());
                    }
                })
                .addNetworkInterceptor(new TokenHeaderInterceptor())
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("retrofit", message);
                        LogToFileUtils.write(message);
                    }
                }).setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public OkHttpClient mClient;

    public static HttpApi getInstance() {
        if (sHttpApi == null) {
            synchronized (HttpApi.class) {
                if (sHttpApi == null) {
                    sHttpApi = new HttpApi();
                }
            }
        }
        return sHttpApi;
    }

    public void post(String url, String body) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);

        Request request = new Request.Builder().url(RetrofitFactory.BASE_URL + url).method("POST", requestBody).build();

        mClient.newCall(request).execute();
    }
}
