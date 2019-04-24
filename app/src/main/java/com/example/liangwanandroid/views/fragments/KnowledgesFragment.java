package com.example.liangwanandroid.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.example.liangwanandroid.R;
import com.example.liangwanandroid.model.knowledge.KnowledgeModelLogic;
import com.example.liangwanandroid.model.knowledge.bean.KnowledgeBean;
import com.example.liangwanandroid.presenter.knowledge.impl.KnowledgePresenterImpl;
import com.example.liangwanandroid.presenter.knowledge.interfaces.KnowledgeContract;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.activitys.KnowledgeDetailActivity;
import com.example.liangwanandroid.views.adapter.KnowledgeListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class KnowledgesFragment extends BaseFragment<KnowledgeModelLogic, KnowledgePresenterImpl> implements KnowledgeContract.View {

    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycleViewContent)
    RecyclerView recyclerView;

    private KnowledgeListAdapter knowledgeListAdapter;
    private List<KnowledgeBean> data;

    @Override
    public int getXmlId() {
        return R.layout.activity_knowledge;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        multipleStatusView.showLoading();
        data = new ArrayList<>();
        mPresenter.initData(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        knowledgeListAdapter = new KnowledgeListAdapter(R.layout.knowledge_recyclerview_item, data);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        knowledgeListAdapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(knowledgeListAdapter);
    }

    private void toContentActivity(KnowledgeBean knowledgeBean) {
        Intent intent = new Intent(getActivity(), KnowledgeDetailActivity.class);
        intent.putExtra("knowledge", knowledgeBean);
        Objects.requireNonNull(getActivity()).startActivity(intent);
        getActivity().overridePendingTransition(R.anim.tranlate_in, R.anim.tranlate_out);
    }

    @Override
    public void showKnowLedge(List<KnowledgeBean> knowledgeBeans) {
        multipleStatusView.showContent();
        swipeRefreshLayout.setRefreshing(false);
        data.clear();
        data.addAll(knowledgeBeans);
        knowledgeListAdapter.setNewData(knowledgeBeans);
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPresenter.initData(getActivity());
        }
    };

    protected BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            toContentActivity(data.get(position));
        }
    };

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
