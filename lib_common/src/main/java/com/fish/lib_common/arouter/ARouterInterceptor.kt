package com.fish.lib_common.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.fish.lib_common.arouter.FlagConst.CERTIFY
import com.fish.lib_common.arouter.FlagConst.LOGIN
import com.fish.lib_common.data.StatusManager
import com.fish.lib_common.extenision.toast
import com.fish.lib_common.util.L

@Interceptor(priority = 1, name = "router")
class ARouterInterceptor : IInterceptor {
    override fun init(context: Context?) {
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val requireFlags = postcard.extra
        L.i(Integer.toBinaryString(requireFlags))

        //当前不需要权限
        if (requireFlags == Integer.MIN_VALUE) {
            callback.onContinue(postcard)
            return
        }
        val currentFlags = StatusManager.instance.getStatus()

        if (requireFlags.or(currentFlags) == currentFlags) {
            callback.onContinue(postcard)
            return
        }

        when {
            requireFlags or LOGIN == LOGIN && currentFlags and LOGIN != LOGIN -> dispatchLogin(postcard, callback)
            requireFlags or CERTIFY == CERTIFY && currentFlags and CERTIFY != CERTIFY -> dispatchCertiry(postcard, callback)
        }

    }

    private fun dispatchLogin(postcard: Postcard, callback: InterceptorCallback) {
        toast("需要登陆")
        callback.onContinue(postcard)
    }

    private fun dispatchCertiry(postcard: Postcard, callback: InterceptorCallback) {
        toast("需要实名")
        callback.onContinue(postcard)
    }

}