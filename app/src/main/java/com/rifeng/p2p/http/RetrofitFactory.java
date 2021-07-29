package com.rifeng.p2p.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.rifeng.p2p.BuildConfig;
import com.rifeng.p2p.callback.FormResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {



//    //正式
//    public static String URL_HOST = "https://guard.rifeng.com/";
//    public static String URL_HOST = "http://192.168.110.66";
    //    public static String URL_HOST = "http://192.168.34.47:8081";
//    public static String URL_HOST = "http://192.168.34.26:8081";
//    //测试
    public static String URL_HOST = "http://192.168.110.66";
//    public static String URL_HOST = "http://192.168.34.26:8081";
    public static String BASE_URL = URL_HOST;
    public static String URL_UPLOAD_HOST = BASE_URL + "/api/upload/uploadMuti";
    public static String REPORT_URL = BASE_URL + "/appchar/";
    //正式
//    public static String URL_HOST = "https://guard.rifeng.com";
//    public static String BASE_URL = URL_HOST;
//    public static String URL_UPLOAD_HOST = "https://guard.rifeng.com" + "/api/upload/uploadMuti";
//    public static String REPORT_URL = "https://guard.rifeng.com/appchar/";

    //正式

//    public static String URL_HOST = "https://guard.rifeng.com";
//    public static String BASE_URL = URL_HOST;
//    public static String URL_UPLOAD_HOST = "https://guard.rifeng.com" + "/api/upload/uploadMuti";
//    public static String REPORT_URL = "https://guard.rifeng.com/appchar/";

    // 获取首页 lookup code


    public static String URL_GET_REGISTER_CONTENT = BASE_URL + "/api/agreement/getAgreementByType/";

    //public static String URL_ADDOPERATION = BASE_URL + "com.sie.rfhw.operationLog.operationLog.addOperationLog.biz.ext";


    public static String URL_GETCOMPANYNAME = BASE_URL + "/api/auth/checkCompany";


    public static String URL_GET_VERIFYCODE_IMG = BASE_URL + "/api/auth/getCaptcha";


    private static final long TIMEOUT = 60;

    private static Handler mDelivery = new Handler(Looper.getMainLooper());


    // Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置
    public static OkHttpClient httpClient = new OkHttpClient.Builder()
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
//                    LogToFileUtils2.write(message);
                }
            }).setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build();

    private static RFService retrofitService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            // 添加Gson转换器
            .addConverterFactory(GsonConverterFactory.create())
            // 添加Retrofit到RxJava的转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(RFService.class);

    public static RFService testService = new Retrofit.Builder()
            .baseUrl(URL_HOST)  //"http://192.168.34.35:8081"
            // 添加Gson转换器
            .addConverterFactory(GsonConverterFactory.create())
            // 添加Retrofit到RxJava的转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(RFService.class);

    public static RFService getInstance() {
        return retrofitService;
    }


    public static void get(Context mContext, final String url, final JSONObject obj, final FormResultListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {

                String urlStr = url;
                String paramStr = getParamString(obj);
                if (!"".equals(paramStr) && paramStr.length() > 0){
                    urlStr = urlStr + "?" + paramStr;
                }
                Log.i("=========:", urlStr);

                Request.Builder requestBuilder = new Request
                        .Builder()
                        .get()//GET请求的参数传递
                        .url(urlStr);
                requestBuilder.addHeader("Content-Type","application/x-www-form-urlencoded");
                Request request = requestBuilder.build();
                try {
                    final Response response = httpClient.newCall(request).execute();
                    if(response.code() == 200){
                        //LogUtil.i("URL:"+ url+"request success");
                        if(listener!=null){
                            mDelivery.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.successResponse(response);
                                }
                            });
                        }

                    }else{
                        if(listener!=null){
                            Log.i(url+"request faile", "");
                            mDelivery.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.faile(response.code()+":Server Error");
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(listener!=null){
                        Log.i(url+"request Exception","");
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.faile("Request Error");
                            }
                        });
                    }

                }
            }
        }).start();

    }

    public static String getParamString(JSONObject obj){
        try {
            StringBuffer sb = new StringBuffer();
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()){
                String key = keys.next();
                String value = obj.getString(key);
                sb.append(key);
                sb.append("=");
                sb.append(value);
                sb.append("&&");
            }
            if (sb.length() > 2) {
                sb.delete(sb.length() - 2, sb.length());
            }

            return sb.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

}
