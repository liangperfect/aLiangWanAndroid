package com.example.liangwanandroid.presenter.common.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.presenter.common.interfaces.SearchContract;

public class SearchPresenterImpl extends SearchContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void getHotKeys(Context context) {

        model.getHotKeys(context).subscribe(listBaseBean -> {

            if (listBaseBean.getErrorCode() == 0) {
                view.showHotKeys(listBaseBean.getData());
            } else {
                view.showError(listBaseBean.getErrorMsg());
            }
        }, throwable -> {

            view.showError("search error:" + throwable.getMessage());
        });
    }

    @Override
    public void saveTag(Context context, String tag) {

        model.saveTag(context, tag);
    }

    @SuppressLint("CheckResult")
    @Override
    public void queryHistory(Context context) {

        model.queryHistory(context)
                .subscribe(searchHistoryBeans -> {
                    if (searchHistoryBeans != null) {
                        view.showHistory(searchHistoryBeans);
                    }
                }, throwable -> {
                    view.showError("query search history error:" + throwable.getMessage());
                });
    }
}
