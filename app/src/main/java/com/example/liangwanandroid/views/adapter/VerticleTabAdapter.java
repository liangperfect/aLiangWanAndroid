package com.example.liangwanandroid.views.adapter;

import android.content.Context;

import com.example.liangwanandroid.model.navigation.bean.NavigationBean;

import java.util.List;

import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;

public class VerticleTabAdapter implements TabAdapter {

    private List<NavigationBean> data;
    private Context context;

    public VerticleTabAdapter(Context context, List<NavigationBean> d) {
        this.context = context;
        this.data = d;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ITabView.TabBadge getBadge(int position) {
        return null;
    }

    @Override
    public ITabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public ITabView.TabTitle getTitle(int position) {
        return new ITabView.TabTitle.Builder().setContent(data.get(position).getName()).build();
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }
}
