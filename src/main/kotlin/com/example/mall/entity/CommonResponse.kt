package com.example.mall.entity

import com.google.gson.GsonBuilder
import java.io.Serializable

/**
 *  Created by Liubin on 2019-04-02
 *
 *  所有接口响应的基类
 *  [stateCode] 状态码
 *  [message] 错误信息
 *  [data] 数据体
 */
class CommonResponse(
        var stateCode: Int = 400,
        var message: String? = "未定义",
        var data: Any? = null
) : Serializable {

    fun success(data: Any?): CommonResponse {
        return CommonResponse(200, "ok", data)
    }

    fun toJsonStr(): String {
        return GsonBuilder().serializeNulls().create().toJson(this)
    }
}