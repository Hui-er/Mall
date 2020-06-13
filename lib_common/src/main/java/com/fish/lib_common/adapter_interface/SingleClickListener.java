package com.fish.lib_common.adapter_interface;

import android.view.View;

import java.util.Calendar;

/**
 * 防抖事件，默认300ms
 */
public abstract class SingleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 300;//这里设置不能超过多长时间
    private long lastClickTime = 0;


    protected abstract void doClick(View v);

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            doClick(v);
        }
    }
}
