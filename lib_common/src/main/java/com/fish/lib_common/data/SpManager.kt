package com.fish.lib_common.data


import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.fish.lib_common.base.BaseApplication
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.URLDecoder
import java.net.URLEncoder

class SpManager private constructor() {

    private val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.app)


    companion object {
        val instance: SpManager = Holder.instance
        const val STATUS = "status"
        const val TOKEN = "token"
    }

    private object Holder {
        val instance = SpManager()
    }

    fun put(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    fun put(key: String, value: Int) {
        sp.edit().putInt(key, value).apply()
    }

    fun put(key: String, value: Boolean?) {
        sp.edit().putBoolean(key, value!!).apply()
    }

    fun getString(key: String): String {
        return sp.getString(key, "").orEmpty()
    }

    fun getInt(key: String): Int {
        return sp.getInt(key, 0)
    }

    fun getBoolean(key: String): Boolean? {
        return sp.getBoolean(key, false)
    }

    fun <T> saveBean(key: String, t: T) {
        try {
            ByteArrayOutputStream().apply {
                ObjectOutputStream(this).let {
                    it.writeObject(t)
                    val result = URLEncoder.encode(this.toString("ISO-8859-1"), "UTF-8")
                    it.close()
                    this.close()
                    put(key, result)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getBean(key: String): T? {
        try {
            getString(key).apply {
                val bytes = URLDecoder.decode(this, "UTF-8").toByteArray(charset("ISO-8859-1"))
                ByteArrayInputStream(bytes).apply {
                    ObjectInputStream(this).let {
                        val any = it.readObject()
                        it.close()
                        this.close()
                        return any as T?
                    }
                }
            }
        } catch (e: Exception) {
            return null
        }
    }


}


