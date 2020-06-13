package com.fish.lib_common.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.fish.lib_common.R
import com.fish.lib_common.widget.refresh.MBClassicsHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        init()
    }

    private fun init() {
        ARouter.debuggable()
        ARouter.openDebug()

        ARouter.init(this)
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setDisableContentWhenRefresh(true)
            layout.setDisableContentWhenLoading(true)
            layout.setPrimaryColorsId(R.color._80F54050, android.R.color.white)
            MBClassicsHeader(context)
        }

    }

    /**
     * 当系统内存严重不足时主动gc
     */
    override fun onLowMemory() {
        super.onLowMemory()
        if (Integer.parseInt(System.getProperty("java.vm.version")!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]) < 2) {
            System.gc()
        }
    }

    companion object {
        lateinit var app: BaseApplication
    }
}
