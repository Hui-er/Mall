package com.fish.lib_common.base.frame;

import androidx.annotation.Nullable;

import com.fish.lib_common.http.HttpInterface;
import com.fish.lib_common.http.RxService;

public abstract class BaseModel<P extends BasePresenter> implements IModel {
    protected P mPresenter;

    public BaseModel(P presenter) {
        this.mPresenter = presenter;
    }

    protected abstract void detach();

    @Override
    public void showLoading(@Nullable String msg) {
        mPresenter.showLoading(msg);
    }

    public HttpInterface getHttpInterface() {
        return RxService.Companion.getInstance().createApi(HttpInterface.class);
    }
}
