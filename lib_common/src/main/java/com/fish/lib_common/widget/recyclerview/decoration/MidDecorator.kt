package com.fish.lib_common.widget.recyclerview.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.core.lib.viewjar.recyclerview.decoration.Decorator
import com.fish.lib_common.R
import com.fish.lib_common.base.BaseApplication

/**
 * 中间分割线  create by LiuBin 2020-05-14
 */
class MidDecorator @JvmOverloads constructor(
    private var decorator: Decorator? = null,
    private var leftWidth: Int = 18,
    private var rightWidth: Int = 0,
    private var divideHeight: Float = 0.5f
) : Decorator {

    private val whitePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dividePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val context = BaseApplication.app

    init {

        whitePaint.color = ContextCompat.getColor(context, R.color._FFFFFF)
        whitePaint.style = Paint.Style.FILL

        dividePaint.color = ContextCompat.getColor(context, R.color._E8E8E8)
        dividePaint.style = Paint.Style.FILL
    }


    override fun drawRect(c: Canvas, p: RecyclerView, v: View, count: Int, cur: Int) {
        if (cur < count - 1) {
            //画左边
            if (leftWidth > 0)
                c.drawRect(Rect().apply {
                    left = 0
                    right = leftWidth
                    top = v.bottom
                    bottom = (v.bottom + divideHeight).toInt()
                }, whitePaint)

            //画中间
            c.drawRect(Rect().apply {
                left = leftWidth
                right = p.width - rightWidth
                top = v.bottom
                bottom = (v.bottom + divideHeight).toInt()
            }, dividePaint)

            //画右边
            if (rightWidth > 0) {
                c.drawRect(Rect().apply {
                    left = p.width - rightWidth
                    right = p.width
                    top = v.bottom
                    bottom = (v.bottom + divideHeight).toInt()
                }, whitePaint)
            }
        }
        decorator?.drawRect(c, p, v, count, cur)
    }
}
