package com.fish.lib_common.extenision

import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.fish.lib_common.adapter_interface.SingleClickListener
import com.fish.lib_common.util.FilterUtil
import com.knowledge.mnlin.linemenuview.LineMenuListener
import com.knowledge.mnlin.linemenuview.LineMenuView


/**
 * 添加onClick防抖动监听
 */
fun <T : View> T.dOnClick(action: T.() -> Unit) {
    this.setOnClickListener(object : SingleClickListener() {
        override fun doClick(v: View?) {
            action(this@dOnClick)
        }
    })
}

/**
 * 添加onClick防抖动监听
 */
fun <T : List<F>, F : View> T.dOnClick(action: F.() -> Unit) {
    this.map {
        it.dOnClick(action = action)
    }
}

/**
 * 添加onClick防抖动监听
 */
fun <T : List<F>, F : View> T.dOnClickIndex(action: Int.() -> Unit) {
    this.mapIndexed { i, v ->
        v.setOnClickListener(object : SingleClickListener() {
            override fun doClick(v: View?) {
                action(i)
            }
        })
    }
}

/**
 * 添加onClick防抖动监听,[action]为需要执行的代码
 *
 * 如果[filter]返回true,则执行注册的事件,否则,不执行
 */
fun <T : View> T.dOnClick(filter: () -> Boolean, action: T.() -> Unit) {
    this.setOnClickListener(object : SingleClickListener() {
        override fun doClick(v: View) {
            if (filter()) action(this@dOnClick)
        }
    })
}

/**
 * LineMenuView设置点击事件
 */
inline fun <T : LineMenuView> T.onPerformSelf(crossinline callback: (LineMenuView) -> Unit) {
    this.setOnClickListener(object : LineMenuListener {
        override fun performSelf(lmv: LineMenuView) {
            callback(lmv)
        }
    })
}

/**
 * LineMenuView设置点击事件
 */
inline fun <T : LineMenuView> T.onPerformLeft(crossinline callback: (TextView) -> Unit) {
    this.setOnClickListener(object : LineMenuListener {
        override fun performClickLeft(tv: TextView): Boolean {
            callback(tv)
            return true
        }
    })
}

/**
 * LineMenuView设置点击事件
 */
inline fun <T : LineMenuView> T.onPerformRight(crossinline callback: (View) -> Unit) {
    this.setOnClickListener(object : LineMenuListener {
        override fun performClickRight(v: View): Boolean {
            callback(v)
            return true
        }
    })
}

/**
 * 输入小数位限制
 */
inline fun <T : EditText> T.addInputFilterDigits(digits: Int) {
    this.filters = this.filters.plus(FilterUtil.createDoubleInputFilter(digits))
}

/**
 * 过滤表情符号
 */
inline fun <T : EditText> T.addFaceFilter() {
    this.filters = this.filters.plus(FilterUtil.setFilter())
}