package com.example.liangwanandroid.views.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.liangwanandroid.R;
import com.example.liangwanandroid.utils.ALogger;
import com.example.liangwanandroid.views.BaseView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class FlashActivity extends BaseActivity {

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    public int getXMLId() {
        return R.layout.activity_flash;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        requestAccess();
    }

    @SuppressLint("CheckResult")
    private void requestAccess() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Permission>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Permission permission) {

                        if (!permission.granted) {
//                            toast(FlashActivity.this, "申请权限失败");
                            ALogger.d("权限状态:" + permission.granted);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Intent i = new Intent(FlashActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    private <T extends Context> void toast(T context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
