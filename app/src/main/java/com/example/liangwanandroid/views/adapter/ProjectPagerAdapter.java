package com.example.liangwanandroid.views.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.liangwanandroid.model.projects.bean.ProjectCategory;
import com.example.liangwanandroid.views.fragments.ProjectListFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public ProjectPagerAdapter(FragmentManager fm, List<ProjectCategory> categories) {
        super(fm);
        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        for (ProjectCategory category : categories) {
            titles.add(category.getName());
            Fragment fragment = ProjectListFragment.getInstance(category.getId());
            fragments.add(fragment);
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

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
