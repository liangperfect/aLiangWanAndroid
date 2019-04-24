package com.example.liangwanandroid.presenter.knowledge.interfaces;

import android.content.Context;

import com.example.liangwanandroid.model.BaseBean;
import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.model.knowledge.bean.KnowledgeBean;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.views.BaseView;
import java.util.List;
import io.reactivex.Observable;

public interface KnowledgeContract {

    interface View extends BaseView {

        void showKnowLedge(List<KnowledgeBean> knowledgeBeans);
    }

    interface Model extends BaseModel {

        Observable<BaseBean<List<KnowledgeBean>>> getKnowledgeData(Context context);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void initData(Context context);
    }
}
