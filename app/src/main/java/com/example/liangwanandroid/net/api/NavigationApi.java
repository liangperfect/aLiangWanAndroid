package com.example.liangwanandroid.net.api;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.navigation.bean.NavigationBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NavigationApi {

    @GET("/navi/json")
    Observable<BaseBean<List<NavigationBean>>> getNavigationData();
}
