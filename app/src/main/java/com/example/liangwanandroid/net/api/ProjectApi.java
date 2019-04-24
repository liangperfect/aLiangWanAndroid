package com.example.liangwanandroid.net.api;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.projects.bean.ProjectCategory;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ProjectApi {

    @GET("/project/tree/json")
    Observable<BaseBean<List<ProjectCategory>>> getProjectsCategory();
}
