package com.example.liangwanandroid.views.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangwanandroid.R;
import com.example.liangwanandroid.events.LoginEvent;
import com.example.liangwanandroid.model.login.LoginModelLogic;
import com.example.liangwanandroid.presenter.login.impl.LoginPresenterImpl;
import com.example.liangwanandroid.presenter.login.interfaces.LoginContract;
import com.example.liangwanandroid.utils.ALogger;
import com.example.liangwanandroid.views.BaseView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginModelLogic, LoginPresenterImpl> implements LoginContract.View {

    @BindView(R.id.edUserName)
    public EditText edUserName;
    @BindView(R.id.edPassword)
    public EditText edPassword;
    @BindView(R.id.edRePassword)
    public EditText edRePassword;
    @BindView(R.id.tvTips)
    public TextView tvTips;
    @BindView(R.id.btnLogin)
    public Button btnLogin;
    @BindView(R.id.btnRegister)
    public Button btnRegister;
    @BindView(R.id.tilUserName)
    public TextInputLayout tilUserName;
    @BindView(R.id.tilPassword)
    public TextInputLayout tilPassword;
    @BindView(R.id.tilRePassword)
    public TextInputLayout tilRePassword;
    boolean isLogin = true;

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public int getXMLId() {
        return R.layout.activity_login;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void loginSuccess(String userName) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new LoginEvent(true, userName));
        LoginActivity.this.finish();
    }

    @Override
    public void loginError(String errorMsg) {
        ALogger.d("登录错误信息:" + errorMsg);
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
        ALogger.d("注册错误信息:" + errorMsg);
    }

    @Override
    public void showError(String msg) {

    }

    @OnClick(R.id.btnLogin)
    public void login() {
        String userNameStr = edUserName.getText().toString().trim();
        String pwdStr = edPassword.getText().toString().trim();
        if (!checkContent(userNameStr, pwdStr)) {
            return;
        }

        mPresenter.requestLogin(this, userNameStr, pwdStr);
    }

    @OnClick(R.id.btnRegister)
    public void register() {
        String userNameStr = edUserName.getText().toString().trim();
        String pwdStr = edPassword.getText().toString().trim();
        String repwdStr = edRePassword.getText().toString().trim();
        if (!checkContent(userNameStr, pwdStr)) {
            return;
        }

        if (TextUtils.isEmpty(repwdStr)) {
            return;
        }

        mPresenter.requestRegister(this, userNameStr, pwdStr, repwdStr);
    }

    @OnClick(R.id.tvTips)
    public void tipOnClick() {

        if (isLogin) {
            isLogin = false;
            tilRePassword.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            tvTips.setText("[登录]");
        } else {
            isLogin = true;
            tilRePassword.setVisibility(View.GONE);
            btnRegister.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            tvTips.setText("[注册]");
        }
    }

    private boolean checkContent(String userName, String password) {

        if (TextUtils.isEmpty(userName)) {
            tilUserName.setError("用户名不能为空");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            tilPassword.setError("密码不能为空");
            return false;
        }

        return true;
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }
}
