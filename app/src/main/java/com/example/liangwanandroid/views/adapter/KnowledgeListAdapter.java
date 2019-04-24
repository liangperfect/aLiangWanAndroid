package com.example.liangwanandroid.views.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liangwanandroid.R;
import com.example.liangwanandroid.model.knowledge.bean.KnowledgeBean;

import java.util.List;

import io.reactivex.Observable;

public class KnowledgeListAdapter extends BaseQuickAdapter<KnowledgeBean, BaseViewHolder> {

    public KnowledgeListAdapter(int layoutResId, @Nullable List<KnowledgeBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void convert(BaseViewHolder helper, KnowledgeBean item) {

        StringBuilder stringBuilder = new StringBuilder();
        Observable.fromIterable(item.getChildren()).subscribe(child -> {
            stringBuilder.append(child.getName());
            stringBuilder.append("  ");
        });
        helper.setText(R.id.idKnowledgeTitle, item.getName())
                .setText(R.id.idKnowledgeDec, stringBuilder.toString());
    }
}
