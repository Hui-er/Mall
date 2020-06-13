package com.fish.lib_common.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.google.gson.Gson
import java.lang.reflect.Type

@Route(path = "/service/json")
class JsonServiceImpl : SerializationService {
    override fun object2Json(instance: Any?): String {
        return Gson().toJson(instance)
    }

    override fun <T> parseObject(input: String?, clazz: Type): T? {
        return Gson().fromJson<T>(input, clazz)
    }

    override fun init(context: Context) {}

    override fun <T> json2Object(input: String, clazz: Class<T>): T? {
        return null
    }
}