package com.example.liangwanandroid.views.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.liangwanandroid.R;
import com.example.liangwanandroid.model.projects.ProjectsModelLogic;
import com.example.liangwanandroid.model.projects.bean.ProjectCategory;
import com.example.liangwanandroid.presenter.projects.impl.ProjectPresenterImpl;
import com.example.liangwanandroid.presenter.projects.interfaces.ProjectContract;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.adapter.ProjectPagerAdapter;

import java.util.List;

import butterknife.BindView;

public class ProjectsFragment extends BaseFragment<ProjectsModelLogic, ProjectPresenterImpl> implements ProjectContract.View {

    @BindView(R.id.idTaLayout)
    public TabLayout tabLayout;

    @BindView(R.id.idProjectPager)
    public ViewPager viewPager;

    private FragmentPagerAdapter adapter;

    @Override
    public int getXmlId() {
        return R.layout.activity_projects;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        mPresenter.initData(getActivity());
    }

    @Override
    public void showTabs(List<ProjectCategory> data) {
        adapter = new ProjectPagerAdapter(getActivity().getSupportFragmentManager(), data);
        viewPager.setAdapter(adapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
