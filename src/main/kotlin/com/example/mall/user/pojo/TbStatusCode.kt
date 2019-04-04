package com.example.mall.user.pojo

import com.google.gson.GsonBuilder
import org.apache.ibatis.type.Alias

/**
 *  Created by Liubin on 2019-04-03
 *
 *  封装状态码
 */
@Alias("statusCode")
data class TbStatusCode(
        var id: Int = 0,
        var statusCode: Int = 0,
        var message: String? = null
) {
    fun toJsonStr(): String {
        return GsonBuilder().serializeNulls().create().toJson(this)
    }

    fun getCacheStatus(): String {
        return "status$statusCode"
    }
}
