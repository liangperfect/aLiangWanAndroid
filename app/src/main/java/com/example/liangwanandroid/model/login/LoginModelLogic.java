package com.example.liangwanandroid.model.login;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.login.bean.LoginBean;
import com.example.liangwanandroid.net.HttpHelper;
import com.example.liangwanandroid.net.api.LoginApi;
import com.example.liangwanandroid.presenter.login.interfaces.LoginContract;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModelLogic implements LoginContract.Model {

    @Override
    public Observable<BaseBean<LoginBean>> login(Context context, String userName, String password) {

        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(LoginApi.class)
                .login(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean<LoginBean>> register(Context context, String userName, String password, String repassword) {

        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(LoginApi.class)
                .register(userName, password, repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
