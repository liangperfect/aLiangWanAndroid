package com.example.liangwanandroid.views.fragments;

import android.annotation.SuppressLint;
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
import com.example.liangwanandroid.model.common.SearchListModelLogic;
import com.example.liangwanandroid.model.home.bean.ArticleDetailData;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.presenter.common.impl.SearchListPresenterImpl;
import com.example.liangwanandroid.presenter.common.interfaces.SearchListContract;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.activitys.ContentActivity;
import com.example.liangwanandroid.views.adapter.ArticlesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class SearchListFragment extends BaseFragment<SearchListModelLogic, SearchListPresenterImpl> implements SearchListContract.View {
    @BindView(R.id.multipleStatusView)
    public MultipleStatusView multipleStatusView;

    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycleViewContent)
    public RecyclerView recyclerView;

    private ArticlesAdapter articlesAdapter;
    private List<ArticleDetailData> articleDetailDatas;
    private int currentPageNO;
    private String key;
    private boolean isInit = true;

    @Override
    public int getXmlId() {
        return R.layout.fragment_search_list;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {

        initData();
        key = getArguments().getString("key");
        mPresenter.getArticlesByKey(getActivity(), 0, key);
    }

    private void initData() {
        articleDetailDatas = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(R.layout.home_recyclerview_item, articleDetailDatas);
        multipleStatusView.showLoading();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        articlesAdapter.bindToRecyclerView(recyclerView);
        articlesAdapter.setOnItemClickListener(onItemClickListener);
        articlesAdapter.setOnItemChildClickListener(onItemChildClickListener);
        articlesAdapter.setOnLoadMoreListener(requestLoadMoreListener);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void showArticles(ArticleList articleList) {
        multipleStatusView.showContent();
        currentPageNO = articleList.getCurPage();
        if (articleList.getDatas() != null) {
            if (isInit) {
                isInit = false;
                if (articleList.getDatas().size() == 0) {
                    multipleStatusView.showEmpty();
                    return;
                }
                swipeRefreshLayout.setRefreshing(false);
                articlesAdapter.setNewData(articleList.getDatas());
                articleDetailDatas.addAll(articleList.getDatas());
                if (articleList.getDatas().size() < 20) {
                    articlesAdapter.loadMoreEnd(true);
                }
            } else {
                articlesAdapter.addData(articleList.getDatas());
                articleDetailDatas.addAll(articleList.getDatas());
                articlesAdapter.loadMoreComplete();
            }
        } else {
            articlesAdapter.loadMoreEnd(true);
        }
    }

    private void toContentActivity(int id, String url, String title) {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        Objects.requireNonNull(getActivity()).startActivity(intent);
        getActivity().overridePendingTransition(R.anim.tranlate_in, R.anim.tranlate_out);
    }

    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            ArticleDetailData article = articleDetailDatas.get(position);
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
            mPresenter.getArticlesByKey(getActivity(), currentPageNO, key);
        }
    };

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            articleDetailDatas.clear();
            isInit = true;
            mPresenter.getArticlesByKey(getActivity(), 0, key);
        }
    };

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
