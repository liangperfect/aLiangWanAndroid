package com.example.liangwanandroid.events;

public class LoginEvent {

    private boolean isLoginSuccess;

    private String userName;

    public LoginEvent(boolean b, String userName) {
        this.isLoginSuccess = b;
        this.userName = userName;
    }

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        isLoginSuccess = loginSuccess;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
