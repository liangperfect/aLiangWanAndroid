package com.example.liangwanandroid.net;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class HttpHelper {

    private static HttpHelper mInstance;
    private Context context;
    private OkHttpClient okHttpClient;

    private HttpHelper(Context context) {
        this.context = context;
        okHttpClient = new OkHttpClient.Builder().
                connectTimeout(45, TimeUnit.SECONDS).
                cookieJar(new CookieManager(context)).
                readTimeout(45, TimeUnit.SECONDS).
                writeTimeout(45, TimeUnit.SECONDS).build();
    }

    public RetrofitClient getRetrofitClient() {

        return new RetrofitClient(okHttpClient);
    }

    public static HttpHelper getInstance(Context context) {

        if (mInstance == null) {
            synchronized (HttpHelper.class) {
                if (mInstance == null) {
                    mInstance = new HttpHelper(context);
                }
            }
        }
        return mInstance;
    }
}

