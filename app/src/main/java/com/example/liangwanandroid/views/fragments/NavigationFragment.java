package com.example.liangwanandroid.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.classic.common.MultipleStatusView;
import com.example.liangwanandroid.R;
import com.example.liangwanandroid.model.navigation.NavigationModelLogic;
import com.example.liangwanandroid.model.navigation.bean.NavigationBean;
import com.example.liangwanandroid.presenter.navigation.NavigationContract;
import com.example.liangwanandroid.presenter.navigation.impl.NavigationPresenterImpl;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.adapter.NavigationAdapter;
import com.example.liangwanandroid.views.adapter.VerticleTabAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.TabView;

public class NavigationFragment extends BaseFragment<NavigationModelLogic, NavigationPresenterImpl> implements NavigationContract.View {

    @BindView(R.id.navVerticalTabLayout)
    public VerticalTabLayout verticalTabLayout;

    @BindView(R.id.navRecyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.navMultipleStatusView)
    public MultipleStatusView multipleStatusView;

    private List<NavigationBean> navigationBeans;
    private NavigationAdapter navigationAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public int getXmlId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        multipleStatusView.showLoading();
        navigationBeans = new ArrayList<>();
        mPresenter.requestData(getActivity());
        verticalTabLayout.addOnTabSelectedListener(onTabSelectedListener);
        navigationAdapter = new NavigationAdapter(R.layout.navigation_recyclerview_item, navigationBeans);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        navigationAdapter.bindToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(List<NavigationBean> data) {
        multipleStatusView.showContent();
        TabAdapter tabAdapter = new VerticleTabAdapter(getActivity(), data);
        verticalTabLayout.setTabAdapter(tabAdapter);
        navigationAdapter.setNewData(data);
    }

    private VerticalTabLayout.OnTabSelectedListener onTabSelectedListener = new VerticalTabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabView tab, int position) {
            recyclerView.stopScroll();
            recyclerView.smoothScrollToPosition(position);
        }

        @Override
        public void onTabReselected(TabView tab, int position) {

        }
    };

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                verticalTabLayout.setTabSelected(linearLayoutManager.findFirstVisibleItemPosition());
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
}
