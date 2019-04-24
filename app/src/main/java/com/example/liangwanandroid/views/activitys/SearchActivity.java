package com.example.liangwanandroid.views.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.liangwanandroid.R;
import com.example.liangwanandroid.model.AppDatabase;
import com.example.liangwanandroid.model.common.SearchModelLogic;
import com.example.liangwanandroid.model.common.bean.HotKeyBean;
import com.example.liangwanandroid.model.common.bean.SearchHistoryBean;
import com.example.liangwanandroid.presenter.common.impl.SearchPresenterImpl;
import com.example.liangwanandroid.presenter.common.interfaces.SearchContract;
import com.example.liangwanandroid.utils.ALogger;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import me.gujun.android.taggroup.TagGroup;

import static com.example.liangwanandroid.views.activitys.CommonActivity.SEARCH_TYPE;

public class SearchActivity extends BaseActivity<SearchModelLogic, SearchPresenterImpl> implements SearchContract.View {
    @BindView(R.id.idToolbar)
    public Toolbar toolbar;

    @BindView(R.id.searchNestScroll)
    public NestedScrollView nestedScrollView;

    @BindView(R.id.tagGroup)
    public TagGroup tagGroup;

    @BindView(R.id.tvClear)
    public TextView textView;

    @BindView(R.id.searchRecyclerView)
    public RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private List<SearchHistoryBean> shbs;

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public int getXMLId() {
        return R.layout.activity_search;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        intiView();
        mPresenter.getHotKeys(SearchActivity.this);
    }

    private void intiView() {
        shbs = new ArrayList<>();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> SearchActivity.this.finish());
        toolbar.setTitle("");
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        historyAdapter = new HistoryAdapter(R.layout.search_history_item, shbs);
        historyAdapter.bindToRecyclerView(recyclerView);
        historyAdapter.setOnItemClickListener(onItemClickListener);
        historyAdapter.setOnItemChildClickListener(onItemChildClickListener);

    }

    @SuppressLint("CheckResult")
    @Override
    public void showHotKeys(List<HotKeyBean> hotKeyBeans) {

        List<String> list = new ArrayList<>();
        Observable.fromIterable(hotKeyBeans).subscribe(hotKeyBean -> {
            list.add(hotKeyBean.getName());
        });
        tagGroup.setTags(list);
        tagGroup.setOnTagClickListener(this::searchKey);
    }

    @Override
    public void showHistory(List<SearchHistoryBean> searchHistoryBeans) {
        if (searchHistoryBeans.size() > 0) {
            shbs.clear();
            shbs.addAll(searchHistoryBeans);
            historyAdapter.setNewData(searchHistoryBeans);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearchView).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(onQueryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showError(String msg) {

        Toast.makeText(SearchActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String tag) {
            searchKey(tag);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String tag) {
            return false;
        }
    };

    private void searchKey(String tag) {
        mPresenter.saveTag(SearchActivity.this, tag);
        Intent intent = new Intent(SearchActivity.this, CommonActivity.class);
        intent.putExtra("fragmentType", SEARCH_TYPE);
        intent.putExtra("key", tag);
        startActivity(intent);
    }

    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            searchKey(shbs.get(position).tag);
        }
    };

    private BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener = new BaseQuickAdapter.OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            historyAdapter.remove(position);
            AppDatabase.getInstance(SearchActivity.this).searchHistoryDao().delete(shbs.get(position));
            shbs.remove(position);
        }
    };

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @OnClick(R.id.tvClear)
    public void OnClearListener() {
        shbs.clear();
        historyAdapter.setNewData(shbs);
        AppDatabase.getInstance(SearchActivity.this).searchHistoryDao().clear();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.queryHistory(SearchActivity.this);
    }
}
