package com.fish.lib_common.base.frame;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends BaseActivity, M extends BaseModel> {

    protected WeakReference<V> mView;
    protected WeakReference<M> mModel;

    public BasePresenter(V view) {
        mView = new WeakReference<>(view);
        mModel = new WeakReference<>(getModel());
    }

    protected abstract M getModel();

    protected void showLoading(@Nullable String msg) {
        if (mView != null && mView.get() != null) {
            mView.get().showLoading(msg);
        }
    }

    protected void detach() {
        if (mView != null && mView.get() != null) {
            mView.clear();
            mView = null;
        }

        if (mModel != null && mModel.get() != null) {
            mModel.get().detach();
        }
    }
}
