package com.fish.lib_common.base.frame

import com.fish.lib_common.http.BaseHttpBean
import com.fish.lib_common.http.HttpDataSource
import com.fish.lib_common.http.HttpInterface
import com.fish.lib_common.http.RxService
import io.reactivex.Observable

abstract class BaseModel {

    val httpInterface: HttpInterface = RxService.instance.createApi(HttpInterface::class.java)

    protected fun <T : BaseHttpBean> requestHttp(
        request: Observable<BaseHttpBean>,
        msg: String? = null,
        loading: ((String?, Boolean) -> Unit)? = null,
        onSuccess: (T: BaseHttpBean) -> Unit,
        onFail: ((Throwable) -> Unit)? = null
    ) {
        HttpDataSource.instance.requestHttp(request, msg, loading, onSuccess, onFail)
    }

    public fun detach() {
        HttpDataSource.instance.removeDisposable()
    }


}
