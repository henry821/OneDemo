package com.demo.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by hengwei on 2022/3/16.
 */

inline val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

inline val Int.dp: Int
    get() = this.toFloat().dp.toInt()

inline val Int.screenWidth: Int
    get() = Resources.getSystem().displayMetrics.widthPixels

inline val Int.screenHeight: Int
    get() = Resources.getSystem().displayMetrics.heightPixels

inline val String.appendThreadInfo: String
    get() = "$this -- ${Thread.currentThread().name}"