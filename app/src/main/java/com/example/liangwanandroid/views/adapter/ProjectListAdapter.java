package com.example.liangwanandroid.views.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liangwanandroid.R;
import com.example.liangwanandroid.model.home.bean.ArticleDetailData;

import org.joda.time.DateTime;

import java.util.List;

public class ProjectListAdapter extends BaseQuickAdapter<ArticleDetailData, BaseViewHolder> {
    public ProjectListAdapter(int layoutResId, @Nullable List<ArticleDetailData> datas) {
        super(layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleDetailData item) {
        DateTime dateTime = new DateTime(item.getPublishTime());
        String publishTime = dateTime.toString("yyyy-MM-dd");
        helper.setText(R.id.projectTitle, item.getTitle())
                .setText(R.id.projectDescription, item.getDesc())
                .setText(R.id.projectTime, publishTime)
                .setText(R.id.projectAuthorName, item.getAuthor());

        if (!item.getEnvelopePic().equals("")) {
            ImageView img = helper.getView(R.id.projectImage);
            Glide.with(mContext).load(item.getEnvelopePic()).into(img);
            img.setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.projectImage).setVisibility(View.GONE);
        }
    }
}
