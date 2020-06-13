package com.fish.lib_common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerAdapter<T>(
    var datas: MutableList<T>,
    @LayoutRes var layoutRes: Int = 0,
    var itemListener: View.(Int) -> Unit,
    var childListeners: List<Pair<Int, View.(Int) -> Unit>> = listOf(),
    var action: View.(T) -> Unit
) : RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        val viewHolder = BaseRecyclerViewHolder(item, itemListener)
        viewHolder.apply {
            childListeners.map {
                item.findViewById<View>(it.first).setOnClickListener { v ->
                    it.second(v, adapterPosition)
                }
            }
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        val mMap = mapOf(0 to "秦", 1 to "川", 2 to "小", 3 to "将")
        action(holder.itemView, datas[position])
    }
}

class BaseRecyclerViewHolder(
    item: View,
    var itemListener: (View, Int) -> Unit
) : RecyclerView.ViewHolder(item) {
    init {
        item.setOnClickListener {
            itemListener(it, adapterPosition)
        }
    }

}
