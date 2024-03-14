package com.demo.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.widget.Toast

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

inline val screenWidth: Int
    get() = Resources.getSystem().displayMetrics.widthPixels

inline val screenHeight: Int
    get() = Resources.getSystem().displayMetrics.heightPixels


fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}