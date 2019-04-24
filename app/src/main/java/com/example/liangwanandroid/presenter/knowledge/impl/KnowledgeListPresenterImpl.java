package com.example.liangwanandroid.presenter.knowledge.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.presenter.knowledge.interfaces.KnowledgeListContract;
import com.example.liangwanandroid.utils.ALogger;

public class KnowledgeListPresenterImpl extends KnowledgeListContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void getKnowledgeArticles(Context context, int pageNo, int cid) {
        model.getKnowledgeArticles(context, pageNo, cid).subscribe(articleListBaseBean ->
                        view.showKnowledgeArticles(articleListBaseBean.getData())
                , throwable -> ALogger.e("get knowledge data error ->" + throwable.getMessage()));
    }
}
