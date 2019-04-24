package com.example.liangwanandroid.presenter.login.interfaces;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.model.login.bean.LoginBean;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.views.BaseView;

import io.reactivex.Observable;

public interface LoginContract {

    interface View extends BaseView {

        void loginSuccess(String userName);

        void loginError(String errorMsg);

        void registerSuccess();

        void registerError(String errorMsg);

    }

    interface Model extends BaseModel {

        Observable<BaseBean<LoginBean>> login(Context context, String userName, String password);

        Observable<BaseBean<LoginBean>> register(Context context, String userName, String password, String repassword);

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void requestLogin(Context context, String userName, String password);

        public abstract void requestRegister(Context context, String userName, String password, String repassword);

    }
}
