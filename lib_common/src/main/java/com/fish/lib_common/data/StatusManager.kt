package com.fish.lib_common.data

import com.fish.lib_common.arouter.FlagConst.FIRST
import com.fish.lib_common.arouter.FlagConst.LOGIN
import com.fish.lib_common.data.SpManager.Companion.STATUS


class StatusManager private constructor() {

    private var currentStatus = SpManager.instance.getInt(STATUS)

    companion object {
        val instance: StatusManager = Holder.instance
    }

    private object Holder {

        val instance = StatusManager()
    }

    fun getStatus():Int{
        return currentStatus
    }

    private fun setStatus() {
        SpManager.instance.put(STATUS, currentStatus)
    }

    private fun changeStatus(value: Int, isAdd: Boolean) {
        when {
            isAdd && value.and(currentStatus) != value -> { //增加一个状态
                currentStatus = value.or(currentStatus)
                setStatus()
            }
            !isAdd && value.and(currentStatus) == value -> { //删除一个状态
                currentStatus = value.xor(currentStatus)
                setStatus()
            }
        }
    }

    fun isFirst(): Boolean {
        return FIRST.and(currentStatus) == FIRST
    }

    fun setFirst() {
        changeStatus(1, true)
    }

    fun isLogin(): Boolean {
        return LOGIN.and(currentStatus) == LOGIN
    }

    fun setLogin(isLogin: Boolean) {
        changeStatus(LOGIN, isLogin)
    }


}
