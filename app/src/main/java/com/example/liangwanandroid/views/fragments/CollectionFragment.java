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
import com.example.liangwanandroid.model.common.CollectionModelLogic;
import com.example.liangwanandroid.model.home.bean.ArticleDetailData;
import com.example.liangwanandroid.model.home.bean.ArticleList;
import com.example.liangwanandroid.presenter.common.impl.CollectionPresenterImpl;
import com.example.liangwanandroid.presenter.common.interfaces.CollectionContract;
import com.example.liangwanandroid.utils.ALogger;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.activitys.ContentActivity;
import com.example.liangwanandroid.views.adapter.ArticlesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class CollectionFragment extends BaseFragment<CollectionModelLogic, CollectionPresenterImpl> implements CollectionContract.View {

    @BindView(R.id.multipleStatusView)
    public MultipleStatusView multipleStatusView;
    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycleViewContent)
    public RecyclerView recycleViewContent;
    private List<ArticleDetailData> data;
    private ArticlesAdapter collectionAdapter;
    private boolean isCompleteRefresh = true;
    private int currentPage;

    @Override
    public int getXmlId() {
        return R.layout.fragment_collection;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {

        multipleStatusView.showLoading();
        data = new ArrayList<>();
        collectionAdapter = new ArticlesAdapter(R.layout.home_recyclerview_item, data);
        collectionAdapter.setOnItemClickListener(onItemClickListener);
        collectionAdapter.setOnItemChildClickListener(onItemChildClickListener);
        collectionAdapter.setOnLoadMoreListener(requestLoadMoreListener, recycleViewContent);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        recycleViewContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleViewContent.setItemAnimator(new DefaultItemAnimator());
        recycleViewContent.setAdapter(collectionAdapter);
        mPresenter.getCollections(getActivity(), 0);
    }

    @Override
    public void showArticleList(ArticleList articles) {
        for (ArticleDetailData articleDetailData : articles.getDatas()) {
            articleDetailData.setCollect(true);
        }

        multipleStatusView.showContent();
        if (isCompleteRefresh) {
            swipeRefreshLayout.setRefreshing(false);
            currentPage = articles.getCurPage();
            data.clear();
            data.addAll(articles.getDatas());
            collectionAdapter.setNewData(articles.getDatas());
            if (articles.getTotal() < articles.getSize()) {
                collectionAdapter.loadMoreEnd(true);
            }
        } else {
            if (!(currentPage == articles.getCurPage())) {
                currentPage = articles.getCurPage();
                data.addAll(articles.getDatas());
                collectionAdapter.addData(articles.getDatas());
                collectionAdapter.loadMoreComplete();
            } else {
                collectionAdapter.loadMoreEnd();
            }
        }
    }

    @Override
    public void deleteArticle(int position) {

        data.remove(position);
        collectionAdapter.remove(position);
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
            ArticleDetailData articleDetailData = data.get(position);
            toContentActivity(articleDetailData.getId(), articleDetailData.getLink(), articleDetailData.getTitle());
        }
    };

    private BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener = new BaseQuickAdapter.OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            switch (view.getId()) {
                case R.id.articleListLikeImg:
                    try {
                        mPresenter.cancelCollectArticle(getActivity(), data.get(position), position);
                    } catch (Exception e) {
                        ALogger.e("收藏点击错误信息:" + e.getMessage());
                    }
                    break;
                default:
                    //nothing
                    break;
            }
        }
    };

    private BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
        @Override
        public void onLoadMoreRequested() {
            isCompleteRefresh = false;
            mPresenter.getCollections(getActivity(), currentPage);
        }
    };

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            isCompleteRefresh = true;
            mPresenter.getCollections(getActivity(), 0);
        }
    };

    @Override
    public void showError(String msg) {

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
