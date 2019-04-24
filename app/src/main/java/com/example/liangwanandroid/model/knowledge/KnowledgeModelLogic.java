package com.example.liangwanandroid.model.knowledge;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.knowledge.bean.KnowledgeBean;
import com.example.liangwanandroid.net.HttpHelper;
import com.example.liangwanandroid.net.api.KnowledgetApi;
import com.example.liangwanandroid.presenter.knowledge.interfaces.KnowledgeContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class KnowledgeModelLogic implements KnowledgeContract.Model {

    @Override
    public Observable<BaseBean<List<KnowledgeBean>>> getKnowledgeData(Context context) {

        return HttpHelper
                .getInstance(context)
                .getRetrofitClient()
                .builder(KnowledgetApi.class)
                .getKnowledgeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
