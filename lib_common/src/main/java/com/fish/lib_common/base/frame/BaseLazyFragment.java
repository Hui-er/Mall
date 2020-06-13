package com.fish.lib_common.base.frame;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseLazyFragment extends Fragment {
    private boolean isViewCreated = false;
    private boolean isUiVisible = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUiVisible = true;
            if (isViewCreated && isUiVisible) {
                lazyLoad();
                //数据加载完毕,恢复标记,防止重复加载
                isViewCreated = false;
                isUiVisible = false;
            }
        } else isUiVisible = false;
    }

    abstract void lazyLoad();
}
