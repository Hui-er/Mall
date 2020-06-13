package com.core.lib.viewjar.recyclerview.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fish.lib_common.R
import com.fish.lib_common.base.BaseApplication
import com.fish.lib_common.util.DisplayUtils

/**
 * 底部分割线  create by LiuBin 2020-05-14
 */
class FooterDecorator @JvmOverloads constructor(
    private var decorator: Decorator? = null,
    private var text: String? = null

) : Decorator {

    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val context = BaseApplication.app

    init {
        textPaint.color = ContextCompat.getColor(context!!, R.color._949494)
        textPaint.textSize = DisplayUtils.sp2px(context, 13f).toFloat()
    }

    override fun drawRect(c: Canvas, p: RecyclerView, v: View, count: Int, cur: Int) {
        text?.apply {
            if (cur == count - 1) {
                val textWidth = textPaint.measureText(this)
                c.drawText(this, (p.width - textWidth) / 2, v.bottom.toFloat() + DisplayUtils.dp2px(context, 30f), textPaint)
            }
        }
        decorator?.drawRect(c, p, v, count, cur)
    }
}
