package com.example.liangwanandroid.model.common;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.net.HttpHelper;
import com.example.liangwanandroid.net.api.CommonApi;
import com.example.liangwanandroid.presenter.common.interfaces.MainContract;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainModelLogic implements MainContract.Model {

    @Override
    public Observable<BaseBean<Object>> logout(Context context) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(CommonApi.class)
                .logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
