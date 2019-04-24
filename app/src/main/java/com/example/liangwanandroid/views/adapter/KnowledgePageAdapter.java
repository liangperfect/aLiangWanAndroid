package com.example.liangwanandroid.views.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.liangwanandroid.model.knowledge.bean.KnowledgeBean;
import com.example.liangwanandroid.utils.ALogger;
import com.example.liangwanandroid.views.fragments.KnowledgeListFragment;

import java.util.ArrayList;
import java.util.List;

public class KnowledgePageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public KnowledgePageAdapter(FragmentManager fm, List<KnowledgeBean.Child> children) {
        super(fm);

        if (fragments != null) {
            fragments.clear();
        }
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        for (KnowledgeBean.Child child : children) {
            titles.add(child.getName());
            ALogger.d("cid->" + child.getName());
            Fragment fragment = KnowledgeListFragment.getInstance(child.getId());
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
