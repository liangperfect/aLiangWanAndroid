package com.example.liangwanandroid.model.projects;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.projects.bean.ProjectCategory;
import com.example.liangwanandroid.net.HttpHelper;
import com.example.liangwanandroid.net.api.ProjectApi;
import com.example.liangwanandroid.presenter.projects.interfaces.ProjectContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectsModelLogic implements ProjectContract.Model {
    @Override
    public Observable<BaseBean<List<ProjectCategory>>> getProjectsCategory(Context context) {

        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(ProjectApi.class)
                .getProjectsCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
