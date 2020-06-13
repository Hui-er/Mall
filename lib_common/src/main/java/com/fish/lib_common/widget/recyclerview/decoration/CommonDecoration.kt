package com.fish.lib_common.widget.recyclerview.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.core.lib.viewjar.recyclerview.decoration.FooterDecorator
import com.fish.lib_common.base.BaseApplication
import com.fish.lib_common.util.DisplayUtils

/**
 * create by LiuBin 2020-05-14
 * 支持左右留空间的分割线,默认最后一条不画
 */

class CommonDecoration @JvmOverloads constructor(
    private var leftWidth: Int = 18,
    private var rightWidth: Int = 0,
    private var topHeight: Int = 0,
    private var divideHeight: Float = 0.5f,
    private var text: String? = null,
    private var bottomHeight: Int = 50

) : RecyclerView.ItemDecoration() {

    fun setText(text: String?) {
        this.text = text
    }

    private var context: Context = BaseApplication.app

    init {
        leftWidth = DisplayUtils.dp2px(context, leftWidth.toFloat())
        rightWidth = DisplayUtils.dp2px(context, rightWidth.toFloat())
        divideHeight = DisplayUtils.dp2px(context, divideHeight).toFloat()
        bottomHeight = DisplayUtils.dp2px(context, bottomHeight.toFloat())
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val cur = parent.getChildAdapterPosition(view)
        val count = parent.adapter?.itemCount ?: 0
        if (cur == 0 && topHeight != 0) outRect.top = topHeight
        if (cur < count - 1) outRect.bottom = divideHeight.toInt()
        if (cur == count - 1 && text != null) outRect.bottom = bottomHeight
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val count = parent.adapter?.itemCount?:0
        for (i in 0 until parent.childCount) {
            val v = parent.getChildAt(i)
            val cur = parent.getChildAdapterPosition(v)
            HeaderDecorator(
                MidDecorator(
                    FooterDecorator(text = text),
                    leftWidth,
                    rightWidth,
                    divideHeight
                )
            ).drawRect(c, parent, v, count, cur)
        }
    }
}
