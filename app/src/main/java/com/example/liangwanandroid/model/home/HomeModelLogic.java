package com.example.liangwanandroid.model.home;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.model.home.bean.BannerDataItem;
import com.example.liangwanandroid.net.HttpHelper;
import com.example.liangwanandroid.net.api.HomeApi;
import com.example.liangwanandroid.presenter.home.interfaces.HomeContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeModelLogic implements HomeContract.Model {

    @Override
    public Observable<BaseBean<List<BannerDataItem>>> getBannerData(Context context) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(HomeApi.class)
                .getHomeBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean<ArticleList>> getArticlesData(Context context, int pageNo) {

        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(HomeApi.class)
                .getArticlesData(pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean<Object>> collectArticle(Context context, int id) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(HomeApi.class)
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean<Object>> cancelCollectArticle(Context context, int id) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(HomeApi.class)
                .cancleCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
