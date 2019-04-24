package com.example.liangwanandroid.presenter.common.interfaces;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.model.home.bean.ArticleDetailData;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.views.BaseView;

import io.reactivex.Observable;

public interface CollectionContract {

    interface View extends BaseView {

        void showArticleList(ArticleList articleList);

        void deleteArticle(int position);
    }

    interface Model extends BaseModel {

        Observable<BaseBean<ArticleList>> getCollections(Context context, int pageNo);

        Observable<BaseBean<Object>> cancelCollectArticle(Context context, int id, int originId);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getCollections(Context context, int pageNo);

        public abstract void cancelCollectArticle(Context context, ArticleDetailData articleDetailData, int position);
    }
}
