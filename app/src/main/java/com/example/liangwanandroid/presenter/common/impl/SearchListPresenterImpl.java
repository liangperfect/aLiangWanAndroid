package com.example.liangwanandroid.presenter.common.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.presenter.common.interfaces.SearchListContract;

public class SearchListPresenterImpl extends SearchListContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void getArticlesByKey(Context context, int page, String key) {
        model.getArticlesByKey(context, page, key).subscribe(articleListBaseBean -> {

            if (articleListBaseBean.getErrorCode() == 0) {

                view.showArticles(articleListBaseBean.getData());
            } else {

                view.showError(articleListBaseBean.getErrorMsg());
            }
        }, throwable -> {

            view.showError("query search list error:" + throwable.getMessage());
        });
    }
}
