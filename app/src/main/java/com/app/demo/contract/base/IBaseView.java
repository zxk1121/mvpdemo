package com.app.demo.contract.base;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * Created by zxk on 17-3-6.
 */

public interface IBaseView {
    void showLoading();

    void hideLoading();

    void showNetError();

    <T> LifecycleTransformer<T> bindToLife();
}
