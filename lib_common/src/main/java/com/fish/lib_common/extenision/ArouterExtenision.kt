package com.fish.lib_common.extenision

import android.app.Activity
import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.fish.lib_common.arouter.Param
import java.io.Serializable


/**
 * 可以在跳转成功后执行一个方法体
 */
inline fun Postcard.navigationWithArrivalRun(context: Context? = null, always: Boolean = false, crossinline onSuccess: () -> Unit) {
    this.navigation(context, object : NavigationCallback {
        /**
         * Callback after lose your way.
         *
         * @param postcard meta
         */
        override fun onLost(postcard: Postcard?) {
            if (always) onSuccess()
        }

        /**
         * Callback when find the destination.
         *
         * @param postcard meta
         */
        override fun onFound(postcard: Postcard?) {
        }

        /**
         * Callback on interrupt.
         *
         * @param postcard meta
         */
        override fun onInterrupt(postcard: Postcard?) {
            if (always) onSuccess()
        }

        /**
         * Callback after navigationWithArrivalRun.
         *
         * @param postcard meta
         */
        override fun onArrival(postcard: Postcard?) {
            onSuccess()
        }
    })
}

/**
 * 简单的路由跳转
 */
infix fun <T> T.route(path: String): Any? {
    return routeCustom(path).navigation()
}

/**
 * 简单的路由跳转,成功后结束自身
 */
inline infix fun <T : Activity> T.routeSuccessFinish(path: String) {
    return ARouter.getInstance().build(path).navigationWithArrivalRun { finish() }
}

/**
 * 自定义路由跳转,只提供Arouter.getInstance.build()的简写,不进行具体逻辑
 */
inline infix fun <T> T.routeCustom(path: String): Postcard = ARouter.getInstance().build(path)

/**
 * 简单的路由跳转,默认参数
 */
inline infix fun <T : Postcard> T.param(any: Any?): Postcard = setParam(Param.KEY, any)

/**
 * 简单的路由跳转:参数1
 */
inline infix fun <T : Postcard> T.firstParam(any: Any?): Postcard = setParam(Param.KEY_ONE, any)

/**
 * 简单的路由跳转:参数2
 */
inline infix fun <T : Postcard> T.secondParam(any: Any?): Postcard = setParam(Param.KEY_TWO, any)

/**
 * 简单的路由跳转:参数3
 */
inline infix fun <T : Postcard> T.thirdParam(any: Any?): Postcard = setParam(Param.KEY_THREE, any)

/**
 * 简单的路由跳转:参数4
 */
inline infix fun <T : Postcard> T.fourParam(any: Any?): Postcard = setParam(Param.KEY_FOUR, any)

/**
 * 简单的路由跳转:参数5
 */
inline infix fun <T : Postcard> T.fiveParam(any: Any?): Postcard = setParam(Param.KEY_FIVE, any)

/**
 * 简单的路由跳转:设置参数
 */
inline fun <T : Postcard> T.setParam(key: String, any: Any?): Postcard =
    when (any) {
        is Int, is Short, is Long, is Float, is Double, is Boolean, is Byte, is Char, is String -> withSerializable(key, any as Serializable)
        else -> withObject(key, any)
    }




