package com.example.liangwanandroid.presenter.knowledge.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.presenter.knowledge.interfaces.KnowledgeContract;
import com.example.liangwanandroid.utils.ALogger;

public class KnowledgePresenterImpl extends KnowledgeContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void initData(Context context) {

        model.getKnowledgeData(context).subscribe(listBaseBean -> view.showKnowLedge(listBaseBean.getData()), throwable -> {
            ALogger.e("knowledge get data error is" + throwable.getMessage());
        });
    }
}
