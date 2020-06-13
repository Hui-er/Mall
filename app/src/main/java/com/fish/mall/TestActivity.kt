package com.fish.mall

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.fish.lib_common.arouter.ARouterConst
import com.fish.lib_common.arouter.Param
import com.fish.lib_common.base.frame.BaseSimpleActivity
import com.fish.lib_common.extenision.dOnClick
import com.fish.lib_common.extenision.route
import kotlinx.android.synthetic.main.activity_test.*

@Route(path = ARouterConst.Activity_TestActivity)
class TestActivity : BaseSimpleActivity() {

    @Autowired(name = Param.KEY)
    @JvmField
    var param: String = ""

    @Autowired(name = Param.KEY_ONE)
    @JvmField
    var count: Int = 1

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initData(savedInstanceState: Bundle?) {
        tv.dOnClick {
            route("/test/liu")
        }
    }


}
