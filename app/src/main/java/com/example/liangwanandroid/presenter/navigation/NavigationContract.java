package com.example.liangwanandroid.presenter.navigation;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.model.navigation.bean.NavigationBean;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.views.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface NavigationContract {

    interface View extends BaseView {

        void showData(List<NavigationBean> data);
    }

    interface Model extends BaseModel {

        Observable<BaseBean<List<NavigationBean>>> getNavigationData(Context context);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void requestData(Context context);
    }
}
