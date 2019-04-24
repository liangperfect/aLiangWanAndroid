package com.example.liangwanandroid.presenter.projects.interfaces;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.model.projects.bean.ProjectCategory;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.views.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface ProjectContract {

    interface View extends BaseView {

        void showTabs(List<ProjectCategory> datas);
    }

    interface Model extends BaseModel {

        Observable<BaseBean<List<ProjectCategory>>> getProjectsCategory(Context context);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void initData(Context context);
    }
}
