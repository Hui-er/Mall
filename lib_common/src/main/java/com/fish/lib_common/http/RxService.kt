package com.fish.lib_common.http

import com.fish.lib_common.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by hpw on 16/11/2.
 */
class RxService {

    companion object {
        val instance: RxService = Holder.instance
    }

    private object Holder {
        var instance = RxService()
    }

    private val TIMEOUT_CONNECTION = 10
    private val TIMEOUT_READ = 20

    private val builder = OkHttpClient.Builder()
        .addInterceptor(MyHttpInterceptor())
        .cache(HttpCache.getCache())
        .connectTimeout(TIMEOUT_CONNECTION.toLong(), TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)

    private val gson = GsonBuilder()
        .serializeNulls()
        .registerTypeAdapterFactory(ListTypeAdapterFactory())//对空列表处理
        .setDateFormat("yyyy:MM:dd HH:mm:ss")
        .registerTypeAdapter(String::class.java, ZeroDeleteAdapter())//0.00值处理
        .create()

    private val okHttpClient: OkHttpClient = getOkHttpClient()

    @Synchronized
    private fun getOkHttpClient(): OkHttpClient {
        return builder.build()
    }

    fun <T> createApi(clazz: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(clazz)
    }

}

