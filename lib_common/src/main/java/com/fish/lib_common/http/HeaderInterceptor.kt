package com.fish.lib_common.http


import com.fish.lib_common.base.BaseApplication
import com.fish.lib_common.data.PersonInfoManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by hpw on 16/11/2.
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = if (!NetUtils.isConnected(BaseApplication.app)) {
            request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .addHeader("token", PersonInfoManager.instance.getToken())
                .build()
        } else {
            request.newBuilder()
                .addHeader("token", PersonInfoManager.instance.getToken())
                .build()
        }
        return chain.proceed(request)
    }
}
