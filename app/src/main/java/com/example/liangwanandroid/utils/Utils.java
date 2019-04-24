package com.example.liangwanandroid.utils;

import android.app.Activity;
import android.widget.Toast;

public class Utils {

    public static <T extends Activity> void toast(T context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
