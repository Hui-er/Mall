package com.fish.lib_common.http

import com.fish.lib_common.extenision.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val observable = RxService().createApi(HttpInterface::class.java).login("liubin", "123456")
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                toast("loading") }
            .subscribe({

            }, {

            })

    }
}
