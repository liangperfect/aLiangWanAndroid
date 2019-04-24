package com.example.liangwanandroid.net.api;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.knowledge.bean.KnowledgeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface KnowledgetApi {

    @GET("/tree/json")
    Observable<BaseBean<List<KnowledgeBean>>> getKnowledgeData();
}
