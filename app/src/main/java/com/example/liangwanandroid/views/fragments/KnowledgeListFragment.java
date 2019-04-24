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
import com.example.liangwanandroid.model.knowledge.KnowledgeListModelLogic;
import com.example.liangwanandroid.presenter.knowledge.impl.KnowledgeListPresenterImpl;
import com.example.liangwanandroid.presenter.knowledge.interfaces.KnowledgeListContract;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.activitys.ContentActivity;
import com.example.liangwanandroid.views.adapter.ArticlesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class KnowledgeListFragment extends BaseFragment<KnowledgeListModelLogic, KnowledgeListPresenterImpl> implements KnowledgeListContract.View {

    @BindView(R.id.recycleViewContent)
    public RecyclerView mRecyclerView;
    @BindView(R.id.multipleStatusView)
    public MultipleStatusView multipleStatusView;
    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;
    private List<ArticleDetailData> articleDetailData;
    private ArticlesAdapter articlesAdapter;
    private boolean isCompleteRefresh = true;
    private int currentPage = 0;

    public static KnowledgeListFragment getInstance(int cid) {
        KnowledgeListFragment knowledgeListFragment = new KnowledgeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cid", cid);
        knowledgeListFragment.setArguments(bundle);
        return knowledgeListFragment;
    }

    @Override
    public int getXmlId() {
        return R.layout.activity_homepage;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        articleDetailData = new ArrayList<>();
        multipleStatusView.showLoading();
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        articlesAdapter = new ArticlesAdapter(R.layout.home_recyclerview_item, articleDetailData);
        articlesAdapter.setOnItemClickListener(onItemClickListener);
        articlesAdapter.setOnItemChildClickListener(onItemChildClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        articlesAdapter.setOnLoadMoreListener(requestLoadMoreListener);
        mRecyclerView.setAdapter(articlesAdapter);
        mPresenter.getKnowledgeArticles(getActivity(), 0, getArguments().getInt("cid"));
    }

    @Override
    public void showKnowledgeArticles(ArticleList data) {
        multipleStatusView.showContent();
        swipeRefreshLayout.setRefreshing(false);
        if (isCompleteRefresh) {
            swipeRefreshLayout.setRefreshing(false);
            articlesAdapter.setNewData(data.getDatas());
            articleDetailData.clear();
            articleDetailData.addAll(data.getDatas());
            if (data.getDatas().size() < 20) {
                articlesAdapter.loadMoreEnd(false);
            }
        } else {
            articlesAdapter.addData(data.getDatas());
            articleDetailData.addAll(data.getDatas());
            currentPage = data.getCurPage();
            if (data.getDatas().size() < 20) {
                articlesAdapter.loadMoreEnd(false);
            } else {
                articlesAdapter.loadMoreComplete();
            }
        }
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void toContentActivity(int id, String url, String title) {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        Objects.requireNonNull(getActivity()).startActivity(intent);
        getActivity().overridePendingTransition(R.anim.tranlate_in, R.anim.tranlate_out);
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPresenter.getKnowledgeArticles(getActivity(), 0, getArguments().getInt("cid"));
        }
    };

    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            isCompleteRefresh = true;
            ArticleDetailData article = articleDetailData.get(position);
            if (article != null) {
                toContentActivity(article.getId(), article.getLink(), article.getTitle());
            }
        }
    };

    private BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener = new BaseQuickAdapter.OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        }
    };

    private BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            isCompleteRefresh = false;
            mPresenter.getKnowledgeArticles(getActivity(), currentPage + 1, getArguments().getInt("cid"));
        }
    };
}
