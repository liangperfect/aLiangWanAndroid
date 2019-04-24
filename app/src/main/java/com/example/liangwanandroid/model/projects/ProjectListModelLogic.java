package com.example.liangwanandroid.model.projects;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.net.HttpHelper;
import com.example.liangwanandroid.net.api.ProjectListApi;
import com.example.liangwanandroid.presenter.projects.interfaces.ProjectListContract;
import com.example.liangwanandroid.utils.ALogger;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectListModelLogic implements ProjectListContract.Model {
    @Override
    public Observable<BaseBean<ArticleList>> getProjectArticles(Context context, int pageNo, int cid) {
        ALogger.d("绑定了几次 pageNo:" + pageNo + "  cid:" + cid);
        return HttpHelper.getInstance(context)
                .getRetrofitClient()
                .builder(ProjectListApi.class)
                .getProjectArticles(pageNo, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
