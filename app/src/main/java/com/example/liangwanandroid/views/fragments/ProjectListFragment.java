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
import com.example.liangwanandroid.model.home.bean.ArticleDetailData;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.model.projects.ProjectListModelLogic;
import com.example.liangwanandroid.presenter.projects.impl.ProjectListPresenterImpl;
import com.example.liangwanandroid.presenter.projects.interfaces.ProjectListContract;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.activitys.ContentActivity;
import com.example.liangwanandroid.views.adapter.ProjectListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class ProjectListFragment extends BaseFragment<ProjectListModelLogic, ProjectListPresenterImpl> implements ProjectListContract.View {

    @BindView(R.id.multipleStatusView)
    public MultipleStatusView multipleStatusView;

    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycleViewContent)
    public RecyclerView recyclerView;

    private ProjectListAdapter projectListAdapter;
    private List<ArticleDetailData> datas;

    private boolean isCompleteRefresh = true;

    private int currentPage = 1;

    public static ProjectListFragment getInstance(int cid) {
        ProjectListFragment projectListFragment = new ProjectListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cid", cid);
        projectListFragment.setArguments(bundle);
        return projectListFragment;
    }

    @Override
    public int getXmlId() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        multipleStatusView.showLoading();
        datas = new ArrayList<>();
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        projectListAdapter = new ProjectListAdapter(R.layout.fragment_project_list_item, datas);
        projectListAdapter.setOnItemClickListener(onItemClickListener);
        projectListAdapter.setOnLoadMoreListener(requestLoadMoreListener, recyclerView);
        recyclerView.setAdapter(projectListAdapter);
        mPresenter.getProjectArticles(getActivity(), 1, getArguments().getInt("cid"));
    }

    private void toContentActivity(int id, String url, String title) {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        Objects.requireNonNull(getActivity()).startActivity(intent);
        getActivity().overridePendingTransition(R.anim.tranlate_in, R.anim.tranlate_out);
    }

    @Override
    public void showArticlesList(ArticleList data) {
        multipleStatusView.showContent();
        swipeRefreshLayout.setRefreshing(false);
        currentPage = data.getCurPage();
        if (isCompleteRefresh) {
            projectListAdapter.setNewData(data.getDatas());
            this.datas.clear();
            this.datas.addAll(data.getDatas());
            if (data.getDatas().size() < 20) {
                projectListAdapter.loadMoreEnd(false);
            }
        } else {
            projectListAdapter.addData(data.getDatas());
            this.datas.addAll(data.getDatas());
            if (data.getDatas().size() < 20) {
                projectListAdapter.loadMoreEnd(false);
            } else {
                projectListAdapter.loadMoreComplete();
            }
        }
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            isCompleteRefresh = true;
            mPresenter.getProjectArticles(getActivity(), 1, getArguments().getInt("cid"));
        }
    };

    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            ArticleDetailData articleDetailData = datas.get(position);
            toContentActivity(articleDetailData.getId(), articleDetailData.getLink(), articleDetailData.getTitle());
        }
    };

    private BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            isCompleteRefresh = false;
            mPresenter.getProjectArticles(getActivity(), currentPage + 1, getArguments().getInt("cid"));
        }
    };


    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
