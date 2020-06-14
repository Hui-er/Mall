package com.fish.login

import com.fish.lib_common.base.frame.BaseModel
import com.fish.lib_common.http.BaseHttpBean

class LoginModel : BaseModel() {

    public fun getData(code: String, uuid: String, msg: String? = null, loading: ((String?, Boolean) -> Unit)) {
        requestHttp<BaseHttpBean>(httpInterface.login("15007253417", code, uuid), msg, loading, onSuccess = {

        })
    }

}
