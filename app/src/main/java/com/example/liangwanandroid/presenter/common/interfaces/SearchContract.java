package com.example.liangwanandroid.presenter.common.interfaces;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.model.common.bean.HotKeyBean;
import com.example.liangwanandroid.model.common.bean.SearchHistoryBean;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.views.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface SearchContract {

    interface View extends BaseView {

        void showHotKeys(List<HotKeyBean> hotKeyBeans);

        void showHistory(List<SearchHistoryBean> searchHistoryBeans);
    }

    interface Model extends BaseModel {

        Observable<BaseBean<List<HotKeyBean>>> getHotKeys(Context context);

        Observable<List<SearchHistoryBean>> queryHistory(Context context);

        void saveTag(Context context, String tag);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getHotKeys(Context context);

        public abstract void saveTag(Context context, String tag);

        public abstract void queryHistory(Context context);
    }
}
