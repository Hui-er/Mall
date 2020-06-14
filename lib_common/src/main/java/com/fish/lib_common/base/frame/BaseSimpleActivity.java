package com.fish.lib_common.base.frame;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.fish.lib_common.base.AppManager;
import com.fish.lib_common.rxbus.RxBusManager;
import com.fish.lib_common.util.L;
import com.fish.lib_common.util.StatusBarUtil;
import com.fish.lib_common.widget.dialog.ProgressDialog;

/**
 * 不需要用mvp模式的时候可以用这个
 */
public abstract class BaseSimpleActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.mContext = this;
        setStatusBar();
        setContentView(getLayoutId());
        if (getLayoutId() != 0) setContentView(getLayoutId());
        AppManager.Companion.getAppManager().addActivity(this);
        ARouter.getInstance().inject(this);
        initData(savedInstanceState);
    }

    protected void setStatusBar() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }

    protected abstract int getLayoutId();

    protected abstract void initData(@Nullable Bundle savedInstanceState);


    protected void showLoading(String msg, boolean isShow) {
        if (isShow) {
            if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
                ProgressDialog.getInstance(mContext)
                        .setMessage(msg)
                        .show();
            } else {
                runOnUiThread(() -> ProgressDialog.getInstance(mContext)
                        .setMessage(msg)
                        .show());
            }
        } else ProgressDialog.getInstance(mContext).dismiss();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.Companion.getAppManager().finishActivity(this);
        L.i("注销" + getClass().getSimpleName());
        RxBusManager.unRegister(this);
    }

}
