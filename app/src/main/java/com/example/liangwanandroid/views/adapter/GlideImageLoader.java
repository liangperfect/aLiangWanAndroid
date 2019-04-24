package com.example.liangwanandroid.views.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.liangwanandroid.model.home.bean.BannerDataItem;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object bannerData, ImageView imageView) {
        BannerDataItem bannerDataItem = (BannerDataItem) bannerData;
        Glide.with(context).load(bannerDataItem.getImagePath()).into(imageView);
    }

    //方便自定义View的创建
    @Override
    public ImageView createImageView(Context context) {
        return super.createImageView(context);
    }
}
