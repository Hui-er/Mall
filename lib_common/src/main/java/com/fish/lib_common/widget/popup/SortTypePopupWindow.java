package com.fish.lib_common.widget.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fish.lib_common.R;
import com.fish.lib_common.util.DisplayUtils;

/**
 * Created by  kuangbs on 2019-12-02.
 */
public class SortTypePopupWindow {

    private SortTypePopupWindow() {
    }

    public static SortTypePopupWindow getInstance() {
        return Holder.popupWindow;
    }

    private static class Holder {
        private static SortTypePopupWindow popupWindow = new SortTypePopupWindow();
    }

    private PopupWindow popupWindow;

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public boolean isShowing() {
        return popupWindow != null && popupWindow.isShowing();
    }

    public void show(Context context, View showView, @NonNull RecyclerView.Adapter adapter) {
        this.show(context, showView, adapter, 0);
    }

    public void show(Context context, View showView, @NonNull RecyclerView.Adapter adapter, int height) {
        this.show(context, showView, adapter, height, false);
    }

    private long dismissTime = 0;

    public void show(Context context, View showView, @NonNull RecyclerView.Adapter adapter, int height, boolean isRight) {
        if (System.currentTimeMillis() - dismissTime < 100) {
            dismissTime = 0;
            return;
        }
        if (popupWindow == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.popup_sort_type, null);
            RecyclerView rvSortType = view.findViewById(R.id.rl_sort_type);
            FrameLayout frameLayout = view.findViewById(R.id.fl_popup_parent);
            rvSortType.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            rvSortType.setAdapter(adapter);
            int screenHeight = DisplayUtils.getScreenHeight(context);
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            popupWindow.setOnDismissListener(() -> {
                popupWindow = null;
                dismissTime = System.currentTimeMillis();
            });
            popupWindow.setAnimationStyle(R.style.budget_detail_popup_anim);
            rvSortType.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int rvWidth = rvSortType.getMeasuredWidth();
            int[] location = new int[2];
            showView.getLocationInWindow(location);
            int showX = (location[0] + showView.getWidth() / 2) - rvWidth / 2;
            FrameLayout.LayoutParams rvLayoutParams = (FrameLayout.LayoutParams) rvSortType.getLayoutParams();
            if (isRight) {
                rvLayoutParams.gravity = Gravity.END;
            } else {
                rvLayoutParams.leftMargin = showX;
            }
            if (height != 0) {
                rvLayoutParams.height = height;
            }
            rvSortType.setLayoutParams(rvLayoutParams);
            FrameLayout.LayoutParams parentLayoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
            parentLayoutParams.height = screenHeight - location[1] - showView.getHeight();
            frameLayout.setLayoutParams(parentLayoutParams);
            frameLayout.setOnClickListener(v -> dismiss());
            popupWindow.showAtLocation(showView, Gravity.NO_GRAVITY, 0, location[1] + showView.getHeight());
        }
    }

}
