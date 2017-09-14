package com.aaron.applicationtemplate;


import com.aaron.utils.klog.KLog;

import java.lang.ref.WeakReference;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Presenter 的基类
 *
 * @author Aaron
 *         <p>
 *         基于Rx的Presenter封装,控制订阅的生命周期
 *         unsubscribe() 这个方法很重要，
 *         因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，
 *         这个引用如果不能及时被释放，将有内存泄露的风险。
 */
public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected WeakReference<T> mView;//弱应用，内存不够时释放
    protected CompositeSubscription mCompositeSubscription;

    /**
     * 取消订阅
     */
    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 添加订阅，方便销毁
     *
     * @param subscription
     */
    protected void addSubscrebe(Subscription subscription) {
        if (subscription == null) {
            KLog.i("Subscription为空");
            return;
        }
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void attachView(T view) {
        this.mView = new WeakReference<T>(view);
    }

    @Override
    public void detachView() {
        if (mView != null) {
            this.mView.clear();
            this.mView = null;
        }
        unSubscribe();
    }
}
