package com.example.liangwanandroid.presenter.common.interfaces;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.views.BaseView;

import io.reactivex.Observable;

public interface MainContract {

    interface View extends BaseView {
        void logoutSuccess();
    }

    interface Model extends BaseModel {

        Observable<BaseBean<Object>> logout(Context context);

    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void logout(Context context);

    }
}
