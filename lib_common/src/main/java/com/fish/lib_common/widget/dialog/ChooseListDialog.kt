package com.fish.lib_common.widget.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fish.lib_common.R
import com.fish.lib_common.widget.dialog.adapter.SingListAdapter
import kotlinx.android.synthetic.main.dialog_single_list_view.*


/**
 * Created by  kuangbs on 2019/7/18.
 */

class ChooseListDialog(context: Context) : Dialog(context, R.style.dialogOptions) {

    private var title: String? = null
    private var singListAdapter: SingListAdapter<*, *>? = null


    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_single_list_view)
        window!!.setGravity(Gravity.BOTTOM)
        val lp = window!!.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = lp
        window!!.setWindowAnimations(R.style.AnimBottom)
        rcv_list.layoutManager = LinearLayoutManager(context)
        rcv_list.adapter = singListAdapter
    }

    override fun onStart() {
        super.onStart()
        tv_title.setText(title);
    }

    fun setTitle(title: String) {
        this.title = title
        if (tv_title != null) {
            tv_title.text = title
        }
    }


    fun setAdapter(adapter: SingListAdapter<*, *>) {
        this.singListAdapter = adapter
        if (rcv_list != null) {
            rcv_list.adapter = singListAdapter
        }
    }

    fun setBackGroupTransparent() {
        window!!.setDimAmount(0f)
    }

    fun destroy() {
        super.dismiss()
    }
}