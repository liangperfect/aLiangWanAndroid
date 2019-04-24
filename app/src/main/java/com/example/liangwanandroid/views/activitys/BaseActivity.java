package com.example.liangwanandroid.views.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.presenter.ContractProxy;
import com.example.liangwanandroid.utils.SharedPreferencesUtil;
import com.example.liangwanandroid.views.BaseView;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

public abstract class BaseActivity<M extends BaseModel, P extends BasePresenter> extends AppCompatActivity {

    protected P mPresenter;

    protected abstract BaseView getViewImpl();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getXMLId());
        ButterKnife.bind(this);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        bindMVP();
        init(savedInstanceState);
    }

    /***
     * 有些Activty不适用EventBus
     * @return
     */
    protected boolean useEventBus() {
        return true;
    }

    /**
     * 获取当前Activity xml的id
     *
     * @return
     */
    public abstract int getXMLId();

    /***
     * 当前Activity初始化地方
     */
    public abstract void init(@Nullable Bundle savedInstanceState);

    /***
     * 绑定MVP中的P层
     */
    private void bindMVP() {
        //获取运行中MVP的实际P层
        mPresenter = ContractProxy.getInstance().getPresenterObject(ContractProxy.getPresnterClazz(getClass(), 1));
        mPresenter.attachContext(this);
        if (getViewImpl() == null) {
            try {
                throw new Exception("未设置ViewImpl，MVP中的V");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (mPresenter != null && getViewImpl() != null) {
            ContractProxy.getInstance().bindModel(ContractProxy.getModelClazz(getClass(), 0), mPresenter);
            ContractProxy.getInstance().bindView(getViewImpl(), mPresenter);
        }
    }

    /**
     * 是否登录了
     *
     * @return
     */
    protected boolean isLogin() {
        return (boolean) SharedPreferencesUtil.getData("isLogin", false);
    }

    /***
     * 获取已经登录的用户名
     * @return
     */
    protected String getUserName() {
        return (String) SharedPreferencesUtil.getData("userName", "null");
    }

    /***
     * 是否是夜间模式
     * @return
     */
    protected boolean isNightMode() {
        return (boolean) SharedPreferencesUtil.getData("isNightMode", false);
    }

    /***
     * 设置夜间模式
     */
    protected void setNightMode(boolean j) {
        SharedPreferencesUtil.putData("isNightMode", j);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }
}
