package com.fish.mall

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.fish.lib_common.bean.Student
import com.fish.lib_common.arouter.ARouterConst
import com.fish.lib_common.arouter.FlagConst
import com.fish.lib_common.base.frame.BaseSimpleActivity
import com.fish.lib_common.extenision.*
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = ARouterConst.Activity_MainActivity, extras = FlagConst.FIRST)
class MainActivity : BaseSimpleActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        tv.dOnClick {
            routeCustom(ARouterConst.Activity_TestActivity)
                .param("bin")
                .firstParam(200)
                .navigation()
        }
        tv_one.dOnClick {
            routeCustom(ARouterConst.Activity_LoginActivity)
                .param("bin")
                .firstParam(200)
                .secondParam(Student())
                .navigation()
        }
    }

}
