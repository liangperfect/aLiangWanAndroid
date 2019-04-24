package com.example.liangwanandroid.presenter;

import android.content.Context;

import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.views.BaseView;

/***
 * 每个BasePresenter只会1对1的View和model
 * @param <V>
 * @param <M>
 */
public class BasePresenter<V extends BaseView, M extends BaseModel> implements RootPresenter<V, M> {
    protected Context mContext;
    protected V view;
    protected M model;
    // 如果有RxJava再解决内存溢出的解绑东西

    @Override
    public void attachView(V v) {
        this.view = v;
    }

    @Override
    public void attachModel(M m) {
        this.model = m;
    }

    @Override
    public void detachView(V v) {
        this.view = null;
    }

    @Override
    public void detachModel(M m) {
        this.model = null;
    }

    @Override
    public void attachContext(Context context) {
        this.mContext = context;
    }

    public V getView() {
        return view;
    }

    public M getModel() {
        return model;
    }

}
