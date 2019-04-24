package com.example.liangwanandroid.net.api;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.common.bean.HotKeyBean;
import com.example.liangwanandroid.model.home.bean.ArticleList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommonApi {

    @GET("/lg/collect/list/{pageNo}/json")
    Observable<BaseBean<ArticleList>> getCollections(@Path("pageNo") int pageNo);

    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseBean<Object>> cancelCollectArticle(@Path("id") int id, @Field("originId") int originid);

    @GET("/user/logout/json")
    Observable<BaseBean<Object>> logout();

    @GET("/hotkey/json")
    Observable<BaseBean<List<HotKeyBean>>> getHotKeys();

    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    Observable<BaseBean<ArticleList>> queryArticlesByKey(@Path("page") int page, @Field("k") String key);
}
