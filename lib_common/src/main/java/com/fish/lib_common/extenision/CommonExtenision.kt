package com.fish.lib_common.extenision

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import com.fish.lib_common.BuildConfig
import com.fish.lib_common.base.BaseApplication
import com.fish.lib_common.util.AppUtils
import com.fish.lib_common.util.ToastUtils
import org.json.JSONObject


fun isDebug(): Boolean {
    return BuildConfig.DEBUG
}


/**
 * 显示bigToast,可以返回自身
 */
fun <T> T.toast(msg: String?): T {
    if (AppUtils.isMainThread()) {
        ToastUtils.showBottomToast(msg)
    } else {
        Handler(Looper.getMainLooper()).post {
            ToastUtils.showBottomToast(msg)
        }
    }
    return this
}

/**
 * 显示bigToast,可以返回自身
 */
fun toast(msg: String?) {
    if (AppUtils.isMainThread()) {
        ToastUtils.showBottomToast(msg)
    } else {
        Handler(Looper.getMainLooper()).post {
            ToastUtils.showBottomToast(msg)
        }
    }

}


/**
 * 多用于 when 条件控制
 */
infix fun <T : Any?> T.means(comment: String): T = this


infix fun <F> Array<F>.initValue(value: F) {
    for (index in 0 until this.size) {
        this[index] = value
    }
}

/**
 * 为Triple 添加方式(仿照Pair)
 */
infix fun <A, B, C> Pair<A, B>.too(that: C): Triple<A, B, C> = Triple(this.first, this.second, that)

/**
 * 为Quadra 添加方式(仿照Pair)
 */
infix fun <A, B, C, D> Triple<A, B, C>.tooo(that: D): Quadra<A, B, C, D> = Quadra(this.first, this.second, this.third, that)

/**
 * 为Quadra 添加方式(仿照Pair)
 */
fun <A, B, C, D> Quadra<A, B, C, D>.subEnd(): Triple<A, B, C> = Triple(this.first, this.second, this.third)

/**
 * 重寫空或空串
 */
infix fun <T : String?> T.ifNullOrEmpty(str: String?): String? {
    return if (this.isNullOrEmpty()) str else this
}

/**
 * 判断String是否为json格式
 */
fun String.isJsonFormat(): Boolean {
    return try {
        JSONObject(this)
        true
    } catch (e: Exception) {
        false
    }
}

/**
 * 获取string
 */
fun getString(@StringRes res: Int, vararg formatArgs: Any?): String {
    return BaseApplication.app.getString(res, *formatArgs)
}

/**
 * 获取string
 */
fun getStringArray(@ArrayRes res: Int): Array<String> {
    return BaseApplication.app.resources.getStringArray(res)
}


/**
 * 获取颜色值
 */
fun dispatchGetColor(@ColorRes resId: Int): Int {
    return if (Build.VERSION.SDK_INT < 23) {
        BaseApplication.app.resources.getColor(resId)
    } else {
        BaseApplication.app.resources.getColor(resId, null)
    }
}

/**
 * 获取尺寸
 */
inline fun dispatchGetDimen(@DimenRes resId: Int): Int {
    return BaseApplication.app.resources.getDimensionPixelSize(resId)
}