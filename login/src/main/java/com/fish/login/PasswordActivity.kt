package com.fish.login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.fish.lib_common.arouter.ARouterConst
import com.fish.lib_common.base.frame.BaseSimpleActivity
import com.fish.lib_common.extenision.dOnClick
import com.fish.lib_common.extenision.route
import kotlinx.android.synthetic.main.activity_password.*

@Route(path = ARouterConst.Activity_PasswordActivity)
class PasswordActivity : BaseSimpleActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_password
    }

    override fun initData(savedInstanceState: Bundle?) {
        tv.dOnClick {
            route(ARouterConst.Activity_TestActivity)
        }
    }

}
