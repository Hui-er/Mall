package com.fish.lib_common.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//动态权限申请
public class PermissionUtils {

    private PermissionUtils() {
    }

    public static class Instance {
        public static PermissionUtils permissionUtils = new PermissionUtils();
    }

    AlertDialog dialog = null;

    @SuppressLint("CheckResult")
    public void requestPermission(final Activity activity, OnPermissionListener onPermissionListener, String... permissions) {

        Observable.timer(0, TimeUnit.SECONDS)
                .compose(new RxPermissions((FragmentActivity) activity).ensure(permissions))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission) {
                        onPermissionListener.onPermission();
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (String per : permissions) {
                            if (per.equals(Manifest.permission.READ_PHONE_STATE)) {
                                sb.append("获取手机信息、");
                            }
                            if (per.equals(Manifest.permission.ACCESS_FINE_LOCATION) || per.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                                sb.append("定位、");
                            }
                            if (per.equals(Manifest.permission.READ_EXTERNAL_STORAGE) || per.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                sb.append("读写手机存储、");
                            }
                            if (per.equals(Manifest.permission.CAMERA)) {
                                sb.append("相机、");
                            }
                            if (per.equals(Manifest.permission.RECORD_AUDIO)) {
                                sb.append("录音、");
                            }
                        }
                        String content = "相关";
                        if (sb.toString().length() > 0) {
                            content = sb.toString().substring(0, sb.toString().length() - 1);
                        }
                        dialog = new AlertDialog.Builder(activity)
                                .setCancelable(false)
                                .setTitle("权限申请")
                                .setMessage(String.format("为了能正常的使用应用，请开启%s权限", content))
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //开启设置页
                                        PermissionSettingUtils.getPhonePermission(activity);
                                        dialog.dismiss();
                                    }
                                }).create();
                        if (!activity.isFinishing())

                            dialog.show();
                    }
                });
    }

    public interface OnPermissionListener {
        void onPermission();
    }
}
