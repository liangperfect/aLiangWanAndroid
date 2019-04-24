package com.example.liangwanandroid.views.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.liangwanandroid.R;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.fragments.AboutUsFragment;
import com.example.liangwanandroid.views.fragments.CollectionFragment;
import com.example.liangwanandroid.views.fragments.SearchListFragment;

import butterknife.BindView;

public class CommonActivity extends BaseActivity {

    @BindView(R.id.idToolbar)
    public Toolbar toolbar;

    @BindView(R.id.framelayout)
    public FrameLayout frameLayout;

    public static final int COLLECTION_TYPE = 0;
    public static final int NIGHT_MODE_TYPE = 1;
    public static final int SETTING_TYPE = 2;
    public static final int ABOUT_US_TYPE = 3;
    public static final int SEARCH_TYPE = 4;

    Fragment collectionFragment;
    Fragment aboutUsFragment;
    Fragment searchFragment;

    @Override
    public int getXMLId() {
        return R.layout.activity_common;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        int type = getIntent().getIntExtra("fragmentType", COLLECTION_TYPE);
        initFragment();
        selectCommonFragment(type);
    }

    private void initFragment() {

        collectionFragment = new CollectionFragment();
        aboutUsFragment = new AboutUsFragment();
        searchFragment = new SearchListFragment();
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    private void selectCommonFragment(int type) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (type) {
            case COLLECTION_TYPE:

                fragmentTransaction.replace(R.id.framelayout, collectionFragment);
                break;
            case ABOUT_US_TYPE:

                fragmentTransaction.replace(R.id.framelayout, aboutUsFragment);
                break;

            case SEARCH_TYPE:

                String key = getIntent().getStringExtra("key");
                Bundle bundle = new Bundle();
                bundle.putString("key", getIntent().getStringExtra("key"));
                searchFragment.setArguments(bundle);
                toolbar.setTitle(key);
                fragmentTransaction.replace(R.id.framelayout, searchFragment);
                break;
            default:
                //nothing to do
                break;
        }

        fragmentTransaction.commit();
    }
}
