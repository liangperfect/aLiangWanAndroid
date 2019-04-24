package com.example.liangwanandroid.views.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liangwanandroid.R;
import com.example.liangwanandroid.model.navigation.bean.NavigationBean;
import com.example.liangwanandroid.utils.ALogger;
import com.example.liangwanandroid.views.activitys.ContentActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import me.gujun.android.taggroup.TagGroup;

public class NavigationAdapter extends BaseQuickAdapter<NavigationBean, BaseViewHolder> {

    private List<String> titles;

    public NavigationAdapter(int layoutResId, @Nullable List<NavigationBean> data) {
        super(layoutResId, data);
        titles = new ArrayList<>();
    }

//    public void set

    @SuppressLint("CheckResult")
    @Override
    protected void convert(BaseViewHolder helper, NavigationBean item) {

        titles.clear();
        helper.setText(R.id.tvGroupTitle, item.getName());
        TagGroup tagGroup = helper.getView(R.id.tagGroup);
        tagGroup.setOnTagClickListener(tag -> {
            for (NavigationBean.NavigationItem item1 : item.getArticles()) {
                ALogger.d("item1.getChapterName():" + item1.getChapterName() + "   tag:" + tag + "\n"
                        + "   item1.getChapterName().equals(tag):" + item1.getChapterName().equals(tag));

                if (item1.getTitle().equals(tag)) {
                    Intent intent = new Intent(mContext, ContentActivity.class);
                    intent.putExtra("title", item1.getTitle());
                    intent.putExtra("id", item1.getId());
                    intent.putExtra("url", item1.getLink());
                    mContext.startActivity(intent);
                    break;
                }
            }
        });

        Observable.fromIterable(item.getArticles()).subscribe(navigationItem -> {
            titles.add(navigationItem.getTitle());
        });
        tagGroup.setTags(titles);
    }
}
