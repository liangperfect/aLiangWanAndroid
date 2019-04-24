package com.example.liangwanandroid.model.common;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.net.HttpHelper;
import com.example.liangwanandroid.net.api.CommonApi;
import com.example.liangwanandroid.presenter.common.interfaces.SearchListContract;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchListModelLogic implements SearchListContract.Model {

    @Override
    public Observable<BaseBean<ArticleList>> getArticlesByKey(Context context, int page, String key) {
        return HttpHelper.getInstance(context)
                .getRetrofitClient()
                .builder(CommonApi.class)
                .queryArticlesByKey(page, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
