package com.example.liangwanandroid.net.api;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.home.bean.ArticleList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KnowledgeListApi {

    @GET("/article/list/{pageNo}/json")
    Observable<BaseBean<ArticleList>> getKnowledgeArticles(@Path("pageNo") int pageNo, @Query("cid") int cid);
}
