package com.fish.lib_common.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.fish.lib_common.extenision.toast

@Route(path = "/degrade/DegradeServiceImpl")
class DegradeServiceImpl:DegradeService {
    override fun onLost(context: Context?, postcard: Postcard?) {
    }

    override fun init(context: Context?) {
    }
}