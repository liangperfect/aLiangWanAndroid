package com.example.liangwanandroid.views.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.classic.common.MultipleStatusView;
import com.example.liangwanandroid.R;
import com.example.liangwanandroid.model.home.HomeModelLogic;
import com.example.liangwanandroid.model.home.bean.ArticleDetailData;
import com.example.liangwanandroid.model.home.bean.BannerDataItem;
import com.example.liangwanandroid.presenter.home.impl.HomePresenterImpl;
import com.example.liangwanandroid.presenter.home.interfaces.HomeContract;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.activitys.ContentActivity;
import com.example.liangwanandroid.views.activitys.LoginActivity;
import com.example.liangwanandroid.views.adapter.ArticlesAdapter;
import com.example.liangwanandroid.views.adapter.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class HomePageFragment extends BaseFragment<HomeModelLogic, HomePresenterImpl> implements HomeContract.View {

    @BindView(R.id.recycleViewContent)
    public RecyclerView mRecyclerView;
    @BindView(R.id.multipleStatusView)
    public MultipleStatusView multipleStatusView;
    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;
    private ArticlesAdapter articlesAdapter;
    private List<ArticleDetailData> articleDetailData;
    private Banner bannerView;

    @Override
    public int getXmlId() {
        return R.layout.activity_homepage;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onInitView(Bundle savedInstanceState) {
        articleDetailData = new ArrayList<>();
        multipleStatusView.showLoading();
        mPresenter.initData(getActivity());
        View mBannerView = getLayoutInflater().inflate(R.layout.home_banner, null);
        bannerView = mBannerView.findViewById(R.id.banner);
        bannerView.setImageLoader(new GlideImageLoader());
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.initData(getActivity()));
        articlesAdapter = new ArticlesAdapter(R.layout.home_recyclerview_item, articleDetailData);
        articlesAdapter.setOnItemClickListener(onItemClickListener);
        articlesAdapter.setOnItemChildClickListener(onItemChildClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        articlesAdapter.addHeaderView(mBannerView);
        articlesAdapter.bindToRecyclerView(mRecyclerView);
        articlesAdapter.setOnLoadMoreListener(() -> mPresenter.loadMoreArticles(getActivity(), articleDetailData.size() / 20), mRecyclerView);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("CheckResult")
    @Override
    public void showBanner(final List<BannerDataItem> bannerDatas) {
        final List<String> imagePaths = new ArrayList<>();
        final List<String> titles = new ArrayList<>();
        Observable.fromIterable(bannerDatas).subscribe(bannerDataItem -> {
            imagePaths.add(bannerDataItem.getImagePath());
            titles.add(bannerDataItem.getTitle());
        });
        bannerView.setImages(bannerDatas);
        bannerView.setBannerTitles(titles);
        bannerView.setDelayTime(2500);
        bannerView.setOnBannerListener(position -> {
            toContentActivity(bannerDatas.get(position).getId(), bannerDatas.get(position).getUrl(), bannerDatas.get(position).getTitle());
        });
        bannerView.start();
    }

    @SuppressLint("CheckResult")
    @Override
    public void showArticleList(List<ArticleDetailData> articles) {
        multipleStatusView.showContent();
        swipeRefreshLayout.setRefreshing(false);
        articlesAdapter.setNewData(articles);
        articleDetailData.clear();
        articleDetailData.addAll(articles);
        if (articles.size() < 20) {
            articlesAdapter.loadMoreEnd(false);
        }
    }

    @Override
    public void loadMoreArticles(List<ArticleDetailData> articles) {
        articlesAdapter.addData(articles);
        articleDetailData.addAll(articles);
        if (articles.size() < 20) {
            articlesAdapter.loadMoreEnd(false);

        } else {
            articlesAdapter.loadMoreComplete();
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

    private ArticlesAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            ArticleDetailData article = articleDetailData.get(position);
            if (article != null) {
                toContentActivity(article.getId(), article.getLink(), article.getTitle());
            }
        }
    };

    private ArticlesAdapter.OnItemChildClickListener onItemChildClickListener = new BaseQuickAdapter.OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            switch (view.getId()) {
                case R.id.articleListLikeImg:
                    if (isLogin()) {
                        ArticleDetailData data = articleDetailData.get(position);
                        boolean isCollect = data.isCollect();
                        data.setCollect(!isCollect);
                        articlesAdapter.setData(position, data);
                        if (!isCollect) {
                            mPresenter.collectArticle(getActivity(), data);
                        } else {
                            mPresenter.cancelCollectArticle(getActivity(), data);
                        }
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
