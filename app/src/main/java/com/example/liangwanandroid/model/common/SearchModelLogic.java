package com.example.liangwanandroid.model.common;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.model.AppDatabase;
import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.common.bean.HotKeyBean;
import com.example.liangwanandroid.model.common.bean.SearchHistoryBean;
import com.example.liangwanandroid.net.HttpHelper;
import com.example.liangwanandroid.net.api.CommonApi;
import com.example.liangwanandroid.presenter.common.interfaces.SearchContract;
import com.example.liangwanandroid.utils.ALogger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchModelLogic implements SearchContract.Model {

    @Override
    public Observable<BaseBean<List<HotKeyBean>>> getHotKeys(Context context) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(CommonApi.class)
                .getHotKeys()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveTag(Context context, String tag) {

        Observable
                .just(tag)
                .observeOn(Schedulers.io())
                .subscribe(s -> {
                    SearchHistoryBean searchHistoryBean1 = AppDatabase.getInstance(context).searchHistoryDao().queryByTag(tag);
                    if (searchHistoryBean1 == null) {
                        SearchHistoryBean searchHistoryBean = new SearchHistoryBean();
                        searchHistoryBean.tag = tag;
                        AppDatabase.getInstance(context)
                                .searchHistoryDao()
                                .insert(searchHistoryBean);
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<List<SearchHistoryBean>> queryHistory(Context context) {

        return Observable.create((ObservableOnSubscribe<List<SearchHistoryBean>>) emitter -> {
            try {
                emitter.onNext(AppDatabase.getInstance(context).searchHistoryDao().queryAll());
            } catch (Exception e) {
                ALogger.e("access db error: " + e.getMessage());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
