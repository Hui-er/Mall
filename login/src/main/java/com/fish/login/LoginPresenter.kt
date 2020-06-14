package com.fish.login

import com.fish.lib_common.base.frame.BasePresenter

class LoginPresenter(view: LoginActivity) : BasePresenter<LoginActivity, LoginModel>(view) {
    override fun getModel(): LoginModel {
        return LoginModel()
    }

    public fun getData(code: String, uuid: String) {
        mModel?.getData(code, uuid, "获取验证码", showLoading)
    }


}
