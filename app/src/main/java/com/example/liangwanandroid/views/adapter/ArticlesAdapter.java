package com.example.liangwanandroid.views.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liangwanandroid.R;
import com.example.liangwanandroid.model.home.bean.ArticleDetailData;

import java.util.List;

public class ArticlesAdapter extends BaseQuickAdapter<ArticleDetailData, BaseViewHolder> {

    public ArticlesAdapter(int layoutResId, @Nullable List<ArticleDetailData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleDetailData item) {

        helper.setText(R.id.articleListAuthorTv, item.getAuthor())
                .setText(R.id.articleListTitleTv, Html.fromHtml(item.getTitle()))
                .setText(R.id.articleListCategoryTv, item.getChapterName())
                .setText(R.id.articleTimeTv, item.getNiceDate())
                .setImageResource(R.id.articleListLikeImg, item.isCollect() ? R.drawable.ic_like : R.drawable.ic_like_not)
                .addOnClickListener(R.id.articleListLikeImg);
        if (!item.getEnvelopePic().equals("")) {
            Glide.with(mContext).load(item.getEnvelopePic())
                    .into((ImageView) helper.getView(R.id.articleListImg));
            //不加这行图片不会显示出来
            helper.getView(R.id.articleListImg).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.articleListImg).setVisibility(View.GONE);
        }
    }
}
