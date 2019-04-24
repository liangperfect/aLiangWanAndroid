package com.example.liangwanandroid.presenter.common.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.model.home.bean.ArticleDetailData;
import com.example.liangwanandroid.presenter.common.interfaces.CollectionContract;
import com.example.liangwanandroid.utils.ALogger;

public class CollectionPresenterImpl extends CollectionContract.Presenter {
    @SuppressLint("CheckResult")
    @Override
    public void getCollections(Context context, int pageNo) {

        model.getCollections(context, pageNo)
                .subscribe(articleListBaseBean -> {
                    if (articleListBaseBean == null) {
                        ALogger.d("请求的结果为空");
                    } else {
                        view.showArticleList(articleListBaseBean.getData());
                    }
                }, throwable -> {
                    ALogger.e("请求收藏数据的错误信息是->" + throwable.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void cancelCollectArticle(Context context, ArticleDetailData articleDetailData, int position) {

        model.cancelCollectArticle(context, articleDetailData.getId(), articleDetailData.getOriginId())
                .subscribe(objectBaseBean -> {

                    if (objectBaseBean.getErrorCode() == 0) {
                        view.deleteArticle(position);
                    } else {
                        ALogger.d("取消收藏失败");
                    }
                }, throwable -> ALogger.e("cancle collect  article error:" + throwable.getMessage()));
    }
}
