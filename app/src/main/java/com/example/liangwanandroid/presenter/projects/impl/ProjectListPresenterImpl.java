package com.example.liangwanandroid.presenter.projects.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.presenter.projects.interfaces.ProjectListContract;

import io.reactivex.functions.Consumer;

public class ProjectListPresenterImpl extends ProjectListContract.Presenter {

    @SuppressLint("CheckResult")
    @Override
    public void getProjectArticles(Context context, int pageNo, int cid) {
        model.getProjectArticles(context, pageNo, cid).subscribe(new Consumer<BaseBean<ArticleList>>() {
            @Override
            public void accept(BaseBean<ArticleList> articleListBaseBean) throws Exception {
                view.showArticlesList(articleListBaseBean.getData());
            }
        });
    }
}
