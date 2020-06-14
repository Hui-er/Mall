package com.fish.lib_common.base.frame

import java.lang.ref.WeakReference

@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
abstract class BasePresenter<V : BaseSimpleActivity, M : BaseModel>(view: V) {

    protected var mView: WeakReference<V>? = null
    protected var mModel: M? = null

    init {
        mView = WeakReference(view)
        mModel = getModel()
    }

    var showLoading = { msg: String?, isShow: Boolean ->
        if (mView != null && mView!!.get() != null) {
            mView?.get()?.showLoading(msg, isShow)
        }
    }

    abstract fun getModel(): M?

    fun detach() {
        if (mView != null && mView!!.get() != null) {
            mView!!.clear()
            mView = null
        }
        mModel?.detach()
        mModel = null
    }
}
