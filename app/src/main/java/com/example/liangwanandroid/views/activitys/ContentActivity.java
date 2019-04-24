package com.example.liangwanandroid.views.activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.liangwanandroid.R;
import com.example.liangwanandroid.utils.ALogger;
import com.example.liangwanandroid.views.BaseView;
import com.just.library.AgentWeb;

import java.util.Objects;

import butterknife.BindView;

public class ContentActivity extends BaseActivity {
    @BindView(R.id.idToolbar)
    public Toolbar toolbar;

    @BindView(R.id.idContentConstraintLayout)
    public ConstraintLayout constraintLayout;

    private AgentWeb agentWeb;

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    public int getXMLId() {
        return R.layout.activity_content;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        toolbar.setNavigationOnClickListener(view -> ContentActivity.this.finish());
        toolbar.setTitle(getIntent().getStringExtra("title"));
        initWebView(getIntent().getStringExtra("url"));
    }

    private void initWebView(String url) {
        agentWeb = AgentWeb.with(this).setAgentWebParent(constraintLayout, new ConstraintLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .defaultProgressBarColor()
                .setReceivedTitleCallback((webView, s) -> ALogger.d("WebView s:" + s))
                .setWebViewClient(webViewClient)
                .setWebChromeClient(webChromeClient)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }
    };

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.action_browser:
                ALogger.d("action_browser");
                break;

            case R.id.action_like:
                ALogger.d("action_like");
                break;

            case R.id.action_share:
                ALogger.d("action_share");
                break;
        }
        return false;
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backWebView();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /***
     * WebView浏览后退，若没有后退则关闭浏览界面
     */
    private void backWebView() {
        boolean isBack = agentWeb.back();
        ALogger.d("后退判断->" + isBack);
        if (!isBack) {
            finish();
        }
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }
}
