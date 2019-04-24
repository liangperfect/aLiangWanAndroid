package com.example.liangwanandroid.presenter.projects.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.presenter.projects.interfaces.ProjectContract;
import com.example.liangwanandroid.utils.ALogger;

public class ProjectPresenterImpl extends ProjectContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void initData(Context context) {

        model.getProjectsCategory(context)
                .subscribe(data -> {
                    if (data != null) {
                        view.showTabs(data.getData());
                    }
                }, throwable -> ALogger.e("获取tab数据出现错误:" + throwable.getMessage()));
    }
}
