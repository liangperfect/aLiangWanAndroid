package com.example.liangwanandroid.presenter;

import android.content.Context;

/***
 * 根Presenter 绑定View和Model或解绑View和Model
 * @param <View> View的泛型
 * @param <Model> Model的泛型
 */
public interface RootPresenter<View, Model> {
    /**
     * 绑定View
     *
     * @param view
     */
    void attachView(View view);

    /**
     * 绑定Model
     *
     * @param model
     */
    void attachModel(Model model);

    /**
     * 解绑View
     *
     * @param view
     */
    void detachView(View view);

    /**
     * 解绑Model
     *
     * @param model
     */
    void detachModel(Model model);

    /***
     * 绑定上下文
     * @param context
     */
    void attachContext(Context context);


}
