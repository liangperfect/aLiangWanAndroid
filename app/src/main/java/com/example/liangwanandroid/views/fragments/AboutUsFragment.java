package com.example.liangwanandroid.views.fragments;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangwanandroid.R;
import com.example.liangwanandroid.views.BaseView;

import butterknife.BindView;

public class AboutUsFragment extends BaseFragment {

    @BindView(R.id.imgAboutUs)
    public ImageView imageView;

    @BindView(R.id.tvDesAboutUs)
    public TextView textView;

    @Override
    public int getXmlId() {
        return R.layout.fragment_aboutus;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {

        textView.setText(Html.fromHtml(getResources().getString(R.string.about_content)));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
