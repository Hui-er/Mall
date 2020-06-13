package com.fish.lib_common.extenision

import android.widget.EditText
import java.math.BigDecimal

fun String?.getNullOrValue(): String? {
    return if (this.isNullOrBlank()) null else this
}

fun String?.toListBySeparator(spilt: String = ";"): List<String> {
    return this?.splitToSequence(spilt)?.toList() ?: mutableListOf()
}

fun <T> String?.judgeEmptyForValue(v1: T?, v2: T?): T? {
    return if (this.isNullOrEmpty()) v1 else v2
}

fun String?.getDefaultValue(v1: String?): String? {
    return if (this.isNullOrEmpty()) v1 else this
}

/**
 * String是否匹配某个字符串(reg)
 */
infix fun <T : String> T.matches(regStr: String) = matches(regStr.toRegex())

/**
 * String是否不匹配某个字符串(reg)
 */
infix fun <T : String> T.notMatch(regStr: String) = !matches(regStr.toRegex())


/**
 * double - > string
 */
infix fun String?.toScaleString(scale: Int) = this?.toBigDecimalOrNull()?.toScaleString(scale)

/**
 * double - > string
 */
infix fun <T : Double> T.toScaleString(scale: Int) = toBigDecimal().setScale(scale, BigDecimal.ROUND_HALF_DOWN).toPlainString()

/**
 * decimal - > string
 */
infix fun <T : BigDecimal> T.toScaleString(scale: Int) = setScale(scale, BigDecimal.ROUND_HALF_DOWN).toPlainString()


/**
 * 设置 TextView 字符串
 */
infix fun <T : EditText> T.textStr(value: String?) {
    setText(value)
}


/**
 * double -> string 不使用科学计数法
 */
fun <T : Double?> T.toStringUnGrouping(): String? {
    if (this == null) {
        return null
    } else {
        return this.toBigDecimal().stripTrailingZeros().toPlainString()
    }
}

/**
 * 隐藏邮箱：只显示@前面的首位和末位
 *
 * @param email
 * @return
 */
fun showHideEmail(email: String): String {
    return email.replace("(\\w{2})(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)".toRegex(), "$1*****$3$4")
}


