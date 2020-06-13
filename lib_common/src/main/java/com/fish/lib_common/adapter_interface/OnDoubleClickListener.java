package com.fish.lib_common.adapter_interface;

import android.view.View;

public class OnDoubleClickListener implements View.OnClickListener {
    private static final long DOUBLE_TIME = 250;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTime < DOUBLE_TIME) {
            onDoubleClick(v);
        } else {
            onSingleClick(v);
        }
        lastClickTime = currentTimeMillis;
    }

    public void onDoubleClick(View v) {

    }

    public void onSingleClick(View v) {

    }
}
