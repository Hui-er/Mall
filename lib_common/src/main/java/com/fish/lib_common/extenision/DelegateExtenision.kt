package com.fish.lib_common.extenision

import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleOwner
import java.io.Serializable

/**
 * 委托模式
 */
inline fun <F : Serializable, T : LifecycleOwner> T.viewBind(@IdRes id: Int, default: Serializable? = null): ViewDelegate<F> = ViewDelegate(this, id, default)

/**
 * 委托模式
 */
inline fun <F : Serializable?, T : LifecycleOwner> T.viewBindNull(@IdRes id: Int): ViewDelegateWithNull<F> = ViewDelegateWithNull(this, id)

/**
 * 委托模式
 */
inline fun <F : Any?, T : LifecycleOwner> T.setBindNullable(noinline callback: SetMethodBindNullable<F>.(F?) -> Unit): SetMethodBindNullable<F> = SetMethodBindNullable(callback)

/**
 * 委托模式
 */
inline fun <F : Any, T : LifecycleOwner> T.setBindNotNull(noinline callback: (F) -> Unit): SetMethodBindNotNull<F> = SetMethodBindNotNull(callback)