package com.example.liangwanandroid.presenter.common.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.presenter.common.interfaces.MainContract;
import com.example.liangwanandroid.utils.ALogger;

public class MainPresenterImpl extends MainContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void logout(Context context) {

        ALogger.d("model->"+model);

        model.logout(context).subscribe(objectBaseBean -> {
            view.logoutSuccess();
        }, throwable -> {
            ALogger.e("logout error:" + throwable.getMessage());
        });
    }
}
