package com.fish.lib_common.base.frame;

import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class BaseActivity<P extends BasePresenter> extends BaseSimpleActivity {
    protected P mPresenter;

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mPresenter = getPresenter();
    }

    protected abstract P getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

}
