package com.fish.lib_common.widget.recyclerview.decoration

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.core.lib.viewjar.recyclerview.decoration.Decorator

/**
 * 头部分割线  create by LiuBin 2020-05-14
 */
class HeaderDecorator @JvmOverloads constructor(
        private var decorator: Decorator? = null
) : Decorator {

    override fun drawRect(c: Canvas, p: RecyclerView, v: View, count: Int, cur: Int) {
        if (cur == 0) {
            //todo
        }
        decorator?.drawRect(c, p, v, count, cur)
    }
}
