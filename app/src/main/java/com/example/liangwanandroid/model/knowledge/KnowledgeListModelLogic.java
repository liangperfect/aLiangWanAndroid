package com.example.liangwanandroid.model.knowledge;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.net.HttpHelper;
import com.example.liangwanandroid.net.api.KnowledgeListApi;
import com.example.liangwanandroid.presenter.knowledge.interfaces.KnowledgeListContract;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KnowledgeListModelLogic implements KnowledgeListContract.Model {

    @Override
    public Observable<BaseBean<ArticleList>> getKnowledgeArticles(Context context, int pageNo, int cid) {
        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(KnowledgeListApi.class)
                .getKnowledgeArticles(pageNo, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
