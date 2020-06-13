package com.fish.lib_common.arouter

object ARouterConst {
    const val Activity_MainActivity = "/activity/MainActivity"
    const val Activity_TestActivity = "/activity/TestActivity"

    const val Activity_LoginActivity = "/login/LoginActivity"
    const val Activity_PasswordActivity = "/login/PasswordActivity"
}

object Param {
    const val KEY = "key_common_value"
    const val KEY_ONE = "key_one"
    const val KEY_TWO = "key_two"
    const val KEY_THREE = "key_three"
    const val KEY_FOUR = "key_four"
    const val KEY_FIVE = "key_five"
}

object FlagConst {
    //是否第一次进入
    const val FIRST = 0
    //是否登陆
    const val LOGIN = 1.shl(1)
    //是否实名
    const val CERTIFY = 1.shl(2)
}