package com.example.liangwanandroid.views.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.liangwanandroid.model.projects.bean.ProjectCategory;

import java.util.List;

public class ProjectAdapter extends FragmentPagerAdapter {

    private List<ProjectCategory> datas;
    private List<Fragment> fragments;

    public ProjectAdapter(FragmentManager fm, List<ProjectCategory> datas) {
        super(fm);
        this.datas = datas;
        //初始化project里面的fragment
        for (ProjectCategory projectCategory : datas) {
//            fragments.add()
        }
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) datas.get(position);
    }
}
