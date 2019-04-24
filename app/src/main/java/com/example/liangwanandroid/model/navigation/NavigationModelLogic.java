package com.example.liangwanandroid.model.navigation;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.navigation.bean.NavigationBean;
import com.example.liangwanandroid.net.HttpHelper;
import com.example.liangwanandroid.net.api.NavigationApi;
import com.example.liangwanandroid.presenter.navigation.NavigationContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NavigationModelLogic implements NavigationContract.Model {

    @Override
    public Observable<BaseBean<List<NavigationBean>>> getNavigationData(Context context) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(NavigationApi.class)
                .getNavigationData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
