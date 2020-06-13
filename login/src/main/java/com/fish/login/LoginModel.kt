package com.fish.login

import com.fish.lib_common.base.frame.BaseModel
import com.fish.lib_common.http.HttpDataSource
import io.reactivex.functions.Consumer

class LoginModel(presenter: LoginPresenter) : BaseModel<LoginPresenter>(presenter) {


    private fun getData() {
        HttpDataSource.instance.requestHttp(httpInterface.login("liu", "123"), "loading", Consumer {

        })
    }

    override fun detach() {
        HttpDataSource.instance.removeDisposable()
    }
}
