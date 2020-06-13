package com.fish.lib_common.base.frame;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.fingdo.statelayout.StateLayout;
import com.fish.lib_common.R;

import org.jetbrains.annotations.Nullable;


/**
 * Created by julong on 2018/12/5.
 * 带多种状态的activity  https://github.com/fingdo/stateLayout
 * //提前设置
 * //设置空数据提示文字
 * //stateLayout.setTipText(StateLayout.EMPTY, "empty tip text");
 * //stateLayout.setTipText(StateLayout.EMPTY, R.string.empty_tip);
 * //空数据提示图标
 * //stateLayout.setTipImg(StateLayout.EMPTY, R.drawable.ic_state_empty);
 * //stateLayout.setTipImg(StateLayout.EMPTY, getResources().getDrawable(R.drawable.ic_state_empty));
 * //展示内容的界面
 * stateLayout.showContentView();
 * //展示加载中的界面
 * stateLayout.showLoadingView();
 * //展示没有网络的界面
 * stateLayout.showNoNetworkView();
 * //展示超时的界面
 * stateLayout.showTimeoutView();
 * //展示空数据的界面
 * stateLayout.showEmptyView();
 * //展示错误的界面
 * stateLayout.showErrorView();
 * //展示登录的界面
 * stateLayout.showLoginView();
 * //显示加载界面
 * stateLayout.showLoadingView();
 * //显示自定义界面
 * stateLayout.showCustomView();
 */

public abstract class BaseStateLayoutActivity<P extends BasePresenter> extends BaseActivity<P> {
    protected StateLayout stateLayout;
    FrameLayout container;
    View view;

    @Override
    public int getLayoutId() {
        return R.layout.common_activity_state_layout;
    }

    @Override
    protected void initData(@androidx.annotation.Nullable Bundle savedInstanceState) {
        stateLayout = findViewById(R.id.state_layout);
        container = findViewById(R.id.container);
        view = getLayoutInflater().inflate(getStateLayoutId(), container, false);
        container.addView(view);
        initStateData(savedInstanceState);
    }

    abstract void initStateData(@Nullable Bundle savedInstanceState);


    public abstract int getStateLayoutId();
}
