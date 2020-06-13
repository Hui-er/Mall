package com.fish.lib_common.http

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.text.NumberFormat

/**
     * 用于去除多余的0的adapter
     */
    internal class ZeroDeleteAdapter : TypeAdapter<String>() {
        private var instance: NumberFormat? = null

        init {
            instance = NumberFormat.getInstance()
            instance!!.maximumFractionDigits = 6
            instance!!.minimumFractionDigits = 0
        }

        /**
         * Writes one JSON value (an array, object, string, number, boolean or null)
         * for `value`.
         *
         * @param out
         * @param value the Java object to write. May be null.
         */
        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: String) {
            out.value(value)
        }

        /**
         * Reads one JSON value (an array, object, string, number, boolean or null)
         * and converts it to a Java object. Returns the converted object.
         *
         * @param in
         * @return the converted Java object. May be null.
         */
        @Throws(IOException::class)
        override fun read(`in`: JsonReader): String? {
            val peek = `in`.peek()
            if (peek == JsonToken.NULL) {
                `in`.nextNull()
                return null
            }
            if (peek == JsonToken.BOOLEAN) {
                return java.lang.Boolean.toString(`in`.nextBoolean())
            }
            val value = `in`.nextString()

            //如果string为double格式,则将小数点后多余的0去掉
            if (value.matches("^[\\d]+\\.[\\d]*0$".toRegex())) {
                try {
                    return instance!!.format(java.lang.Double.parseDouble(value)).replace(",", "")
                } catch (ignored: Exception) {

                }
            }
            return value
        }
    }