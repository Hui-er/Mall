package com.core.lib.viewjar.recyclerview.decoration

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface Decorator {
    fun drawRect(c: Canvas, p: RecyclerView, v: View, count: Int, cur: Int)
}
