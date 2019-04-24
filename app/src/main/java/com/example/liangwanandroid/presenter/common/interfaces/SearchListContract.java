package com.example.liangwanandroid.presenter.common.interfaces;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.views.BaseView;

import io.reactivex.Observable;

public interface SearchListContract {

    interface View extends BaseView {

        void showArticles(ArticleList articleList);
    }

    interface Model extends BaseModel {

        Observable<BaseBean<ArticleList>> getArticlesByKey(Context context, int page, String key);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getArticlesByKey(Context context, int page, String key);
    }
}
