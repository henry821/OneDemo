package com.demo.base.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.demo.base.R

/**
 * Description SharedPreference工具类
 * Author henry
 * Date   2023/3/1
 */
fun getAppSharedPreference(context: Context): SharedPreferences {
    return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
}

fun <T> putAppSharedPreferenceValue(context: Context, key: String, data: T) {
    getAppSharedPreference(context).edit {
        when (data) {
            is Int -> putInt(key, data)
            is Long -> putLong(key, data)
            is String -> putString(key, data)
            is Boolean -> putBoolean(key, data)
            is Float -> putFloat(key, data)
            else -> throw IllegalArgumentException("不支持类型")
        }
    }
}