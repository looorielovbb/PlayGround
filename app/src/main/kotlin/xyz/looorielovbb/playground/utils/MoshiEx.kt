package xyz.looorielovbb.playground.utils

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


object MoshiEx {

    abstract class MoshiTypeReference<T> // 自定义的类，用来包装泛型

    const val TAG: String = "MoshiEx"
    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    inline fun <reified T> toJson(src: T, indent: String = ""): String {
        try {
            val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
            return jsonAdapter.indent(indent).toJson(src)
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        }
        return ""
    }

    inline fun <reified T> fromJson(jsonStr: String): T? {
        try {
            val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
            return jsonAdapter.fromJson(jsonStr)
        } catch (e: Exception) {
            Log.e(TAG, "", e)
        }
        return null
    }

    inline fun <reified T> getGenericType(): Type {
        return object :
            MoshiTypeReference<T>() {}::class.java
            .genericSuperclass
            .let { it as ParameterizedType }
            .actualTypeArguments
            .first()
    }
}
