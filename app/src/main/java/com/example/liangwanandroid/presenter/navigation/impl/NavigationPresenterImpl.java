package com.example.liangwanandroid.presenter.navigation.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.presenter.navigation.NavigationContract;
import com.example.liangwanandroid.utils.ALogger;

public class NavigationPresenterImpl extends NavigationContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void requestData(Context context) {

        model.getNavigationData(context)
                .subscribe(listBaseBean -> {
                    view.showData(listBaseBean.getData());
                }, throwable -> {
                    ALogger.e("navigation get error :" + throwable.getMessage());
                });
    }
}
