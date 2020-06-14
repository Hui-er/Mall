package com.fish.login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.fish.lib_common.BuildConfig
import com.fish.lib_common.bean.Student
import com.fish.lib_common.arouter.ARouterConst
import com.fish.lib_common.arouter.FlagConst
import com.fish.lib_common.arouter.Param
import com.fish.lib_common.base.frame.BaseActivity
import com.fish.lib_common.extenision.dOnClick
import com.fish.lib_common.extenision.route
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

@Route(path = ARouterConst.Activity_LoginActivity, extras = FlagConst.LOGIN)
class LoginActivity : BaseActivity<LoginPresenter>() {

    @Autowired(name = Param.KEY)
    @JvmField
    var param: String = ""

    @Autowired(name = Param.KEY_ONE)
    @JvmField
    var count: Int = 1

    @Autowired(name = Param.KEY_TWO)
    @JvmField
    var student: Student? = null

    private var uuid = ""

    override fun getPresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        tv.dOnClick {
            mPresenter.getData(et.text.toString(),uuid)
        }
    }

    override fun onResume() {
        super.onResume()
        loadCode()
    }

    private fun loadCode() {
        et.setText("")
        getRandomStr()
        Glide.with(this)
            .load(BuildConfig.baseUrl+ "/app/captcha.webp?uuid=${uuid}")
            .into(iv_code)
    }

    private fun getRandomStr() {
        uuid = UUID.randomUUID().toString().replace("-", "")
    }
}
