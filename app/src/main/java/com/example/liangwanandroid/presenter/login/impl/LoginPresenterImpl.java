package com.example.liangwanandroid.presenter.login.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.presenter.login.interfaces.LoginContract;
import com.example.liangwanandroid.utils.ALogger;
import com.example.liangwanandroid.utils.SharedPreferencesUtil;

public class LoginPresenterImpl extends LoginContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void requestLogin(Context context, String userName, String password) {

        model.login(context, userName, password).subscribe(data -> {

            if (0 == data.getErrorCode()) {
                SharedPreferencesUtil.putData("userName", data.getData().getUsername());
                SharedPreferencesUtil.putData("isLogin", true);
                view.loginSuccess(userName);
            } else {
                view.loginError(data.getErrorMsg());
            }
        }, throwable -> {
            ALogger.e("login error:" + throwable.getMessage());
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void requestRegister(Context context, String userName, String password, String repassword) {

        model.register(context, userName, password, repassword)
                .subscribe(data -> {
                    if (0 == data.getErrorCode()) {
                        SharedPreferencesUtil.putData("userName", data.getData().getUsername());
                        SharedPreferencesUtil.putData("isLogin", true);
                        view.registerSuccess();
                    } else {
                        view.registerError(data.getErrorMsg());
                    }
                }, throwable -> {
                    ALogger.e("register error:" + throwable.getMessage());
                });
    }
}
