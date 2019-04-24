package com.example.liangwanandroid.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangwanandroid.R;
import com.example.liangwanandroid.events.LoginEvent;
import com.example.liangwanandroid.model.common.MainModelLogic;
import com.example.liangwanandroid.presenter.common.impl.MainPresenterImpl;
import com.example.liangwanandroid.presenter.common.interfaces.MainContract;
import com.example.liangwanandroid.utils.ALogger;
import com.example.liangwanandroid.utils.SharedPreferencesUtil;
import com.example.liangwanandroid.views.BaseView;
import com.example.liangwanandroid.views.fragments.HomePageFragment;
import com.example.liangwanandroid.views.fragments.KnowledgesFragment;
import com.example.liangwanandroid.views.fragments.NavigationFragment;
import com.example.liangwanandroid.views.fragments.ProjectsFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;

import butterknife.BindView;

import static com.example.liangwanandroid.views.activitys.CommonActivity.ABOUT_US_TYPE;
import static com.example.liangwanandroid.views.activitys.CommonActivity.COLLECTION_TYPE;

public class MainActivity extends BaseActivity<MainModelLogic, MainPresenterImpl> implements MainContract.View {

    @BindView(R.id.dl)
    public DrawerLayout mDrawerLayout;
    @BindView(R.id.idToolbar)
    public Toolbar mToolbar;
    @BindView(R.id.frame_content)
    public FrameLayout mFrameLayoutConent;
    @BindView(R.id.bottomnavigationview)
    public BottomNavigationView mBottomnavigationview;
    @BindView(R.id.navigationview)
    public NavigationView mNavigationView;
    private TextView tvUserName;
    private Fragment mHomePageFragment;
    private Fragment mProjectsFragment;
    private Fragment mKnowledgeFragment;
    private Fragment mNavigationFragment;
    private long mFirstTime;

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public int getXMLId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        initFragment();
        setListener();
        initToolbar();
    }

    /***
     *  初始化相关fragment
     */
    private void initFragment() {

        mHomePageFragment = new HomePageFragment();
        mProjectsFragment = new ProjectsFragment();
        mKnowledgeFragment = new KnowledgesFragment();
        mNavigationFragment = new NavigationFragment();
        switchFragment(mHomePageFragment);
    }

    /***
     * 初始化toolbar
     */
    // TODO: 封装到BaseActivity里面去
    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        mToolbar.setTitle("首页");
    }

    private void setListener() {
        tvUserName = mNavigationView.getHeaderView(0).findViewById(R.id.menu_login);
        if (isLogin()) {
            tvUserName.setText(getUserName());
        } else {
            tvUserName.setText("[登录]");
        }
        tvUserName.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        mBottomnavigationview.setLabelVisibilityMode(1);
        mBottomnavigationview.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.homgpage:
                    switchFragment(mHomePageFragment);
                    mToolbar.setTitle("首页");
                    return true;
                case R.id.projets:
                    switchFragment(mProjectsFragment);
                    mToolbar.setTitle("开源项目");
                    return true;
                case R.id.knowledge:
                    switchFragment(mKnowledgeFragment);
                    mToolbar.setTitle("知识体系");
                    return true;
                case R.id.navigation:
                    switchFragment(mNavigationFragment);
                    mToolbar.setTitle("导航");
                    return true;
                default:
                    //nothing to do
                    break;
            }
            return false;
        });

        mNavigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.collection:
                    if (isLogin()) {
                        Intent intent = new Intent(MainActivity.this, CommonActivity.class);
                        intent.putExtra("fragmentType", COLLECTION_TYPE);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    break;

                case R.id.night:
                    if (!isNightMode()) {
                        setNightMode(true);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        setNightMode(false);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                    reCreate();
                    break;

                case R.id.setting:
                    break;

                case R.id.aboutUs:

                    Intent intent = new Intent(MainActivity.this, CommonActivity.class);
                    intent.putExtra("fragmentType", ABOUT_US_TYPE);
                    startActivity(intent);

                    break;

                case R.id.loginOut:

                    loginOut();
                    break;
            }
            return true;
        });
    }

    private void loginOut() {

        new AlertDialog
                .Builder(MainActivity.this)
                .setMessage("确认要退出?")
                .setPositiveButton("确认", (dialogInterface, i) -> {
                    SharedPreferencesUtil.clear();
                    mPresenter.logout(MainActivity.this);
                })
                .setNegativeButton("取消", (dialogInterface, i) -> {

                })
                .create()
                .show();
    }

    private void reCreate() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mHomePageFragment != null) {
            fragmentTransaction.remove(mHomePageFragment);
        }

        if (mProjectsFragment != null) {
            fragmentTransaction.remove(mProjectsFragment);
        }

        if (mKnowledgeFragment != null) {
            fragmentTransaction.remove(mKnowledgeFragment);
        }

        if (mNavigationFragment != null) {
            fragmentTransaction.remove(mNavigationFragment);
        }

        fragmentTransaction.commitAllowingStateLoss();

        super.recreate();
    }

    /***
     * 切换fragment方法
     * @param f 切换Fragment
     */
    public void switchFragment(Fragment f) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, f);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSearch:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

                return true;

            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = DateTime.now().getMillis();

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - mFirstTime < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mFirstTime = DateTime.now().getMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        ALogger.d("onMessageEvent->" + event.isLoginSuccess() + "  name:" + event.getUserName());
        if (!event.isLoginSuccess()) {
            tvUserName.setText("[登录]");
            mNavigationView.getMenu().findItem(R.id.loginOut).setVisible(false);
        } else {
            tvUserName.setText(event.getUserName());
            ALogger.d("在哪个线程中->" + Thread.currentThread().getName());
            mNavigationView.getMenu().findItem(R.id.loginOut).setVisible(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomePageFragment = null;
        mProjectsFragment = null;
        mKnowledgeFragment = null;
        mNavigationFragment = null;
    }

    @Override
    public void logoutSuccess() {

        Toast.makeText(MainActivity.this, R.string.loginout, Toast.LENGTH_SHORT).show();
        LoginEvent loginEvent = new LoginEvent(false, "null");
        EventBus.getDefault().post(loginEvent);
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void showError(String msg) {

        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
