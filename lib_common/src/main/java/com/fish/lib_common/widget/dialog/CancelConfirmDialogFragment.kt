package com.fish.lib_common.widget.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.fish.lib_common.R
import com.fish.lib_common.extenision.dOnClickIndex
import com.fish.lib_common.extenision.means
import com.fish.lib_common.util.DisplayUtils
import kotlinx.android.synthetic.main.dialog_fragment_cancel_confirm.*

/**
 * Created on 2018/1/16
 * function : 简单的删除提示框
 *
 * @author LinkTech
 */

class CancelConfirmDialogFragment(
    private val cancel: ((Dialog?) -> Boolean)?,
    private val confirm: ((Dialog?) -> Boolean)?
) : DialogFragment() {

    private var dialogTitle: String? = null
    private var cancelStr: String? = null
    private var confirmStr: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.ActivityDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //去除标题
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //添加view
        return inflater.inflate(R.layout.dialog_fragment_cancel_confirm, null, false)
    }

    override fun onStart() {
        super.onStart()
        tv_cancel.text = cancelStr
        tv_confirm.text = confirmStr
        tv_title.text = dialogTitle
        dialog?.apply {
            window?.apply {
                setGravity(Gravity.CENTER)
                setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
                setLayout((DisplayUtils.getScreenWidth(requireContext()) * 0.75).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
            }
            setCanceledOnTouchOutside(true)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var consume = false
        listOf(tv_cancel, tv_confirm).dOnClickIndex {
            consume = when (this) {
                0 means "取消" -> cancel?.invoke(dialog) ?: false
                1 means "确定" -> confirm?.invoke(dialog) ?: false
                else -> false
            }
        }
        if (!consume) dialog?.dismiss()
    }

    fun setTitle(title: String): CancelConfirmDialogFragment {
        dialogTitle = title
        return this
    }

    fun setCancelText(cancel: String): CancelConfirmDialogFragment {
        cancelStr = cancel
        return this
    }

    fun setConfirmText(confirm: String): CancelConfirmDialogFragment {
        confirmStr = confirm
        return this
    }
}
