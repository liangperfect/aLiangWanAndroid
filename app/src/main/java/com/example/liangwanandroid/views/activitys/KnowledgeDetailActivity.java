package com.example.liangwanandroid.views.activitys;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.liangwanandroid.R;
import com.example.liangwanandroid.model.knowledge.bean.KnowledgeBean;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.adapter.KnowledgePageAdapter;

import butterknife.BindView;

public class KnowledgeDetailActivity extends BaseActivity {
    @BindView(R.id.idToolbar)
    Toolbar toolbar;

    @BindView(R.id.idTaLayout)
    TabLayout tabLayout;

    @BindView(R.id.idKnowLedgePager)
    ViewPager viewPager;

    KnowledgePageAdapter knowledgeListAdapter;

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    public int getXMLId() {
        return R.layout.activity_knowledge_detail;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        KnowledgeBean knowledgeBean = (KnowledgeBean) getIntent().getSerializableExtra("knowledge");
        if (knowledgeBean != null) {
            knowledgeListAdapter = new KnowledgePageAdapter(getSupportFragmentManager(), knowledgeBean.getChildren());
            toolbar.setTitle(knowledgeBean.getName());
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            viewPager.setAdapter(knowledgeListAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
}
