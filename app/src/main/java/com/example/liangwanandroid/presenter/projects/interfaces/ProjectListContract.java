package com.example.liangwanandroid.presenter.projects.interfaces;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.views.BaseView;

import io.reactivex.Observable;

public interface ProjectListContract {

    interface View extends BaseView {

        void showArticlesList(ArticleList datas);
    }

    interface Model extends BaseModel {

        Observable<BaseBean<ArticleList>> getProjectArticles(Context context, int pageNo, int cid);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getProjectArticles(Context context, int pageNo, int cid);
    }
}
