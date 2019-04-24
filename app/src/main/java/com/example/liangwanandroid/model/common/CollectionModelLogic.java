package com.example.liangwanandroid.model.common;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.net.HttpHelper;
import com.example.liangwanandroid.net.api.CommonApi;
import com.example.liangwanandroid.presenter.common.interfaces.CollectionContract;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CollectionModelLogic implements CollectionContract.Model {

    @Override
    public Observable<BaseBean<ArticleList>> getCollections(Context context, int pageNo) {

        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(CommonApi.class)
                .getCollections(pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean<Object>> cancelCollectArticle(Context context, int id, int originId) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(CommonApi.class)
                .cancelCollectArticle(id, originId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
