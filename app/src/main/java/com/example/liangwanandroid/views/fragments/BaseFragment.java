package com.example.liangwanandroid.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.presenter.BasePresenter;
import com.example.liangwanandroid.presenter.ContractProxy;
import com.example.liangwanandroid.utils.ALogger;
import com.example.liangwanandroid.utils.SharedPreferencesUtil;
import com.example.liangwanandroid.views.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<M extends BaseModel, P extends BasePresenter> extends Fragment {
    private Unbinder mUnbinder;
    private View mRootView;

    protected P mPresenter;

    abstract public int getXmlId();

    /**
     * 获取MVP中的V
     *
     * @return
     */
    protected abstract BaseView getViewImpl();

    protected abstract void onInitView(Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parentView = (ViewGroup) mRootView.getParent();
            if (parentView != null) {
                parentView.removeView(mRootView);
            }
            return mRootView;
        }
        int xmlID = getXmlId();
        ALogger.d("getXmlId:" + xmlID + "   judged:" + (xmlID != 0));
        if (getXmlId() != 0) {
            mRootView = inflater.inflate(getXmlId(), container, false);
        } else {
            mRootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        assert mRootView != null;
        mUnbinder = ButterKnife.bind(this, mRootView);
        ALogger.d("mRootView:" + mRootView.toString());
        bindMVP();
        onInitView(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /***
     * 绑定MVP中的P层
     */
    private void bindMVP() {
        //获取运行中MVP的实际P层
        ALogger.d("绑定了MVP");
        mPresenter = ContractProxy.getInstance().getPresenterObject(ContractProxy.getPresnterClazz(getClass(), 1));
        mPresenter.attachContext(getActivity());
        if (mPresenter != null && getViewImpl() != null) {
            ContractProxy.getInstance().bindModel(ContractProxy.getModelClazz(getClass(), 0), mPresenter);
            ContractProxy.getInstance().bindView(getViewImpl(), mPresenter);
        }
    }

    /***
     * 当前fragment加载的xml view
     * 区别于上面的getViewImpl()方法
     * @return 返回根布局
     */
    public View getRootView() {
        return mRootView;
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

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO: 后续添加解除绑定
//        if (mUnbinder != null) {
//            mUnbinder.unbind();
//        }
//        ContractProxy.getInstance().unBindModel(ContractProxy.getModelClazz(getClass(), 1), mPresenter);
//        ContractProxy.getInstance().unBindView(getViewImpl(), mPresenter);

    }
}
