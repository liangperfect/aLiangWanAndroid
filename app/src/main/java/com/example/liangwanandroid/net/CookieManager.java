package com.example.liangwanandroid.net;

import android.content.Context;

import com.example.liangwanandroid.utils.ALogger;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieManager implements CookieJar {
    private PersistentCookieStore cookieStore;

    public CookieManager(Context context) {
        cookieStore = new PersistentCookieStore(context);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies.size() > 0) {
            for (Cookie cookieItem : cookies) {
                ALogger.d("cookie:".concat(cookieItem.domain()).concat(" ").concat(cookieItem.name()));
                cookieStore.add(url, cookieItem);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.get(url);
    }
}
