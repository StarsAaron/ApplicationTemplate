package com.aaron.applicationtemplate;

/**
 * 功能调用接口
 * Presenter 与 View 的基础接口定义
 *
 * @author Aaron
 */
public interface BaseContract {

    /**
     * Presenter 基类，页面功能抽取
     *
     * @param <T>
     */
    interface BasePresenter<T> {
        /**
         * 绑定视图,在Actiity的生命周期的onResume方法中调用
         *
         * @param view
         */
        void attachView(T view);

        /**
         * 当Activity销毁时必须解绑视图，防止内存泄露
         */
        void detachView();
    }

    /**
     * 页面刷新基础接口，提供Presenter回调刷新视图使用，由页面实现该接口
     */
    interface BaseView {

    }
}
