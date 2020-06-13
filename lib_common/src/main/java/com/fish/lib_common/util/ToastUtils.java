package com.fish.lib_common.util;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fish.lib_common.R;
import com.fish.lib_common.base.BaseApplication;

public class ToastUtils {

    private static Toast mToast;


    /**
     * 非阻塞试显示Toast,防止出现连续点击Toast时的显示问题
     */
    @SuppressLint("ShowToast")
    public static void showToast(CharSequence text) {
        if (mToast == null) {
            mToast = createToast(text);
        } else {
            TextView tvContent = mToast.getView().findViewById(R.id.tv_toast_content);
            tvContent.setText(text);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    /**
     * 非阻塞试显示Toast,防止出现连续点击Toast时的显示问题
     */
    @SuppressLint("ShowToast")
    public static void showBottomToast(CharSequence text) {
        if (mToast == null) {
            mToast = createToast(text);
        } else {
            TextView tvContent = mToast.getView().findViewById(R.id.tv_toast_content);
            tvContent.setText(text);
        }
        mToast.setGravity(Gravity.BOTTOM, 0, 120);
        mToast.show();
    }


    @SuppressLint("ShowToast")
    public static Toast createToast(CharSequence text) {
        Toast toast = Toast.makeText(BaseApplication.app, "", Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(BaseApplication.app).inflate(R.layout.layout_toast_round_rectang, null);
        TextView tvContent = view.findViewById(R.id.tv_toast_content);
        tvContent.setText(text);
        toast.setView(view);
        return toast;
    }

}
