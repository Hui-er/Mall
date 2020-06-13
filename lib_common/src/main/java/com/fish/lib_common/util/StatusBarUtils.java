package com.fish.lib_common.util;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.fish.lib_common.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 浅色状态栏适配工具类
 */
public class StatusBarUtils {

    /**
     * 设置顶部字体颜色为黑色/取消
     */
    public static void setStatusTextBlack(Activity activity, boolean black) {
        //设置更改状态栏字体图标颜色,如果未成功,则再将状态栏设置为半模糊状态
        if (black) {
            if (!StatusBarUtils.setLightStatusBar(activity, true)) {
                //为0表示设置失败,则需要定义一下状态栏的颜色
                setStatusBarColor(activity, activity.getResources().getColor(R.color._33000000));
            }
        } else {
            StatusBarUtils.setLightStatusBar(activity, false);
        }
    }

    /**
     * 改字体颜色
     */
    private static boolean setLightStatusBar(Activity activity, boolean dark) {
        boolean flag = false;
        switch (RomUtils.getLightStatausBarAvailableRomType()) {
            case RomUtils.AvailableRomType.MIUI: //小米
                flag = setMIUILightStatusBar(activity, dark);
                break;
            case RomUtils.AvailableRomType.FLYME: //魅族
                flag = setFlymeLightStatusBar(activity, dark);
                break;
            case RomUtils.AvailableRomType.ANDROID_NATIVE: //23以上
                flag = setAndroidNativeLightStatusBar(activity, dark);
                break;
            case RomUtils.AvailableRomType.NA:
                // N/A do nothing
                break;
            default:
                break;
        }
        return flag;
    }

    /**
     * 改小米手机
     */
    private static boolean setMIUILightStatusBar(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 改魅族手机
     */
    private static boolean setFlymeLightStatusBar(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }


    /**
     * 23以上的
     */
    private static boolean setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        return true;

    }

    /**
     * 设置状态栏颜色
     */
    private static void setStatusBarColor(Activity activity, int color) {
        //api大于21，则让顶部状态栏、底部导航栏透明
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(color);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //使ActionBar消失
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) actionBar.hide();
    }

}
