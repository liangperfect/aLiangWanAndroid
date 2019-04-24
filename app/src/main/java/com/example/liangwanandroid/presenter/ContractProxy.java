package com.example.liangwanandroid.presenter;

import com.example.liangwanandroid.model.BaseModel;
import com.example.liangwanandroid.views.BaseView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ContractProxy {

    private static final ContractProxy m_instance = new ContractProxy();

    public static ContractProxy getInstance() {
        return m_instance;
    }

    /**
     * Model
     * 通过反射, 获得定义Class时声明的父类的泛型参数的类型.
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     * determined
     */
    @SuppressWarnings("unchecked")
    public static Class<BaseModel> getModelClazz(final Class clazz, final int index) {

        //带泛型返回父类的类型
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return BaseModel.class;
        }
        //返回实际的参数类型
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return BaseModel.class;
        }
        if (!(params[index] instanceof Class)) {
            return BaseModel.class;
        }
        return (Class) params[index];
    }

    /**
     * Presenter
     * 通过反射, 获得定义Class时声明的父类的泛型参数的类型.
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     * determined
     */
    @SuppressWarnings("unchecked")
    public static Class<BasePresenter> getPresnterClazz(final Class clazz, final int index) {

        //带泛型返回父类的类型
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return BasePresenter.class;
        }

        //返回实际的参数类型
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return BasePresenter.class;
        }
        if (!(params[index] instanceof Class)) {
            return BasePresenter.class;
        }
        return (Class) params[index];
    }

    /***
     * 获取泛型对应的实际Presenter类型
     * @param <T>
     * @return
     */
    public <T> T getPresenterObject(Class clazz) {
        if (clazz == null) {
            return null;
        }
        BasePresenter p = null;
        try {
            p = (BasePresenter) clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) p;
    }

    /***
     * 获取泛型对应的实际model类型
     * @param clazz
     * @param <V>
     * @return
     */
    public <V> V getModelObject(Class clazz) {
        if (clazz == null) {
            return null;
        }
        BaseModel m = null;
        try {
            m = (BaseModel) clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (V) m;
    }

    /***
     * 将实际中的M层和P层绑定在一起
     * @param model
     * @param presenter
     */
    public <M> M bindModel(Class model, BasePresenter presenter) {
        if (model == null || presenter == null) {
            return null;
        }
        BaseModel m = null;
        try {
            m = (BaseModel) model.newInstance();
            presenter.attachModel(m);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (M) m;
    }

    /***
     * 将实际中的V层和P层绑定在一起
     * @param view
     * @param presenter
     * @param <V>
     * @return
     */
    public <V> V bindView(BaseView view, BasePresenter presenter) {
        if (view == null || presenter == null) {
            return null;
        }
        presenter.attachView(view);
        return (V) view;
    }

    /**
     * 将P和V层进行分离
     *
     * @param baseView
     * @param presenter
     */
    public void unBindView(BaseView baseView, BasePresenter presenter) {
        if (presenter != null) {
            if (baseView != presenter.getView()) {
                presenter.detachView(baseView);
            }
        }
    }

    /***
     * 将P层和M层进行分离
     * @param clazz
     * @param presenter
     */
    public void unBindModel(Class clazz, BasePresenter presenter) {
        BaseModel baseModel = null;
        try {
            baseModel = (BaseModel) clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        if (baseModel != null) {
            if (baseModel != presenter.getModel()) {
                presenter.detachModel(baseModel);
            }
        }
    }


}
