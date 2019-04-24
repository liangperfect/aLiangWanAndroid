package com.example.liangwanandroid.views.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liangwanandroid.R;
import com.example.liangwanandroid.model.common.bean.SearchHistoryBean;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<SearchHistoryBean, BaseViewHolder> {

    public HistoryAdapter(int layoutResId, @Nullable List<SearchHistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistoryBean item) {
        helper.setText(R.id.historyTag, item.tag);
        helper.addOnClickListener(R.id.historyDelete);
    }
}
