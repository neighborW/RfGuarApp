package com.rifeng.p2p.http;

import android.util.Log;

import com.rifeng.p2p.app.BaseApp;
import com.rifeng.p2p.manager.DataManager;
import com.rifeng.p2p.util.StringUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        if (DataManager.getInstance().getUser() != null) {
            String token = DataManager.getInstance().getToken();
            if (!StringUtils.isEmpty(token)) {

                Request originalRequest = chain.request();
                Request updateRequest = originalRequest.newBuilder().header("token", token).build();
                return chain.proceed(updateRequest);
            }
        } else if (chain.request().url().toString().contains("/api/auth/agreeAggrement")) {
            String token = DataManager.getInstance().getAccountInfoDataInfo().getToken();
            if (!StringUtils.isEmpty(token)) {
                Request originalRequest = chain.request();
                Request updateRequest = originalRequest.newBuilder().header("token", token).build();
                return chain.proceed(updateRequest);
            }
        }


        Request originalRequest = chain.request();
        return chain.proceed(originalRequest);

    }
}
