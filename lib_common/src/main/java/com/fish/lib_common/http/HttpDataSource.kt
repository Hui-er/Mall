package com.fish.lib_common.http


import com.fish.lib_common.extenision.toast
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
    fun <T : BaseHttpBean> requestHttp(observable: Observable<T>, msg: String, success: Consumer<T>, fail: Consumer<Throwable>?=null) {
        taskCount++
        val disposable = observable
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { dis -> toast(msg) }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ responseBean ->
                taskCount--
                success.accept(responseBean)
            }, { throwable ->
                taskCount--
                fail?.accept(throwable)
            })
        group.add(disposable)
    }

    fun removeDisposable() {
        group.dispose()
    }

}
