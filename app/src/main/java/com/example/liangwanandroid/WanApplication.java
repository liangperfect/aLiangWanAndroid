package com.example.liangwanandroid;

import android.app.Application;

import com.example.liangwanandroid.utils.SharedPreferencesUtil;

public class WanApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesUtil.getInstance(getApplicationContext(), "wanAndroid");
    }
}
