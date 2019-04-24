package com.example.liangwanandroid.presenter.knowledge.interfaces;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.views.BaseView;

import io.reactivex.Observable;

public interface KnowledgeListContract {

    interface View extends BaseView {
        void showKnowledgeArticles(ArticleList data);
    }

    interface Model extends BaseModel {

        Observable<BaseBean<ArticleList>> getKnowledgeArticles(Context context, int pageNo, int cid);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getKnowledgeArticles(Context context, int pageNo, int cid);
    }
}
