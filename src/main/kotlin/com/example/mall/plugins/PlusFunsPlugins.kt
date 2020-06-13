package com.example.mall.plugins

import com.example.mall.util.StatusCodeUtil
import com.example.mall.util.StatusCodeUtil.*
import javax.servlet.http.HttpServletResponse

/**
 * 多用于 when 条件控制
 */
@Suppress("NOTHING_TO_INLINE")
inline infix fun <T : Any?> T.means(comment: String): T = this


@Suppress("NOTHING_TO_INLINE")
inline fun <T : Boolean> T.write(response: HttpServletResponse, code: Int): T {
    response.writer.print(StatusCodeUtil.getStatusCode(code).toJsonStr())
    return this
}

/**
 * 专门重写Boolean方法,true时执行
 */
inline infix fun Boolean.onTrue(block: () -> Unit): Boolean {
    if (this) {
        block()
    }
    return this
}