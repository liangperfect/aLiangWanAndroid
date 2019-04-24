package com.example.liangwanandroid.presenter.home.interfaces;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.model.home.bean.ArticleDetailData;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.model.home.bean.BannerDataItem;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.views.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface HomeContract {

    interface View extends BaseView {

        void showBanner(List<BannerDataItem> banners);

        void showArticleList(List<ArticleDetailData> articles);

        void loadMoreArticles(List<ArticleDetailData> articles);
    }

    interface Model extends BaseModel {

        Observable<BaseBean<List<BannerDataItem>>> getBannerData(Context context);

        Observable<BaseBean<ArticleList>> getArticlesData(Context context, int pageNo);

        Observable<BaseBean<Object>> collectArticle(Context context, int id);

        Observable<BaseBean<Object>> cancelCollectArticle(Context context, int id);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        /***
         * 初始化首页数据
         * @param context
         */
        public abstract void initData(Context context);

        /***
         * 加载更多数据
         * @param context
         */
        public abstract void loadMoreArticles(Context context, int page);

        /**
         * 收藏文章
         *
         * @param data
         */
        public abstract void collectArticle(Context context, ArticleDetailData data);

        /***
         * 取消收藏
         * @param data
         */
        public abstract void cancelCollectArticle(Context context, ArticleDetailData data);
    }
}
