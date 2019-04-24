package com.example.liangwanandroid.presenter.home.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.model.home.bean.ArticleDetailData;
import com.example.liangwanandroid.presenter.home.interfaces.HomeContract;
import com.example.liangwanandroid.utils.ALogger;

public class HomePresenterImpl extends HomeContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void initData(Context context) {

        /***
         * 初始化banner数据
         */
        model.getBannerData(context).subscribe(dataItemBaseBean -> {
            if (dataItemBaseBean != null && dataItemBaseBean.getData() != null) {
                view.showBanner(dataItemBaseBean.getData());
            }
        }, throwable -> ALogger.e("get banner data error:" + throwable.getMessage()));

        model.getArticlesData(context, 0).subscribe(articleListBaseBean -> {
            view.showArticleList(articleListBaseBean.getData().getDatas());
        }, throwable -> ALogger.e("get articles data error:" + throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadMoreArticles(Context context, int page) {
        model.getArticlesData(context, page).subscribe(articleListBaseBean -> {
            view.loadMoreArticles(articleListBaseBean.getData().getDatas());
        }, throwable -> ALogger.e("get banner data error:" + throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void collectArticle(Context context, ArticleDetailData data) {

        model.collectArticle(context, data.getId()).subscribe(objectBaseBean -> {
            if (objectBaseBean.getErrorCode() == 0) {
                ALogger.d("收藏成功");
            } else {
                ALogger.d("收藏失败");
            }
        }, throwable -> ALogger.e("collect article error:" + throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void cancelCollectArticle(Context context, ArticleDetailData data) {

        model.cancelCollectArticle(context, data.getId()).subscribe(objectBaseBean -> {

            if (objectBaseBean.getErrorCode() == 0) {
                ALogger.d("收藏成功");
            } else {
                ALogger.d("收藏失败");
            }
        }, throwable -> ALogger.e("cancle collect article error:" + throwable.getMessage()));
    }
}

