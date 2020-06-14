package com.fish.lib_common.http


import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class HttpDataSource private constructor() {

    private var group: CompositeDisposable = CompositeDisposable()

    @Volatile
    var taskCount: Int = 0

    companion object {
        val instance: HttpDataSource = Holder.instance
    }

    private object Holder {
        var instance = HttpDataSource()
    }

    @Synchronized
    fun <T : BaseHttpBean> requestHttp(observable: Observable<T>, msg: String? = null, loading: ((String?, Boolean) -> Unit)? = null, success: (T) -> Unit, fail:((Throwable)->Unit)? = null) {
        loading?.apply { taskCount++ }
        val disposable = observable
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { loading?.invoke(msg, true) }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ responseBean ->
                taskCount--
                success.invoke(responseBean)
                if (taskCount == 0) loading?.invoke(null, false)
            }, { throwable ->
                taskCount--
                fail?.invoke(throwable)
                if (taskCount == 0) loading?.invoke(null, false)
            })
        group.add(disposable)
    }

    fun removeDisposable() {
        group.dispose()
    }

}
