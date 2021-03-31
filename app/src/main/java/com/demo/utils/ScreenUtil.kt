package com.demo.utils

import android.content.Context

object ScreenUtil {

    fun getScreenWidth(ctx: Context): Int {
        return ctx.resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(ctx: Context): Int {
        return ctx.resources.displayMetrics.heightPixels
    }

    fun getScreenDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    fun convertPxToDp(ctx: Context, px: Int): Int {
        return ((px / getScreenDensity(ctx) + 0.5f).toInt())
    }

    fun convertDpToPx(ctx: Context, dp: Int): Int {
        return ((getScreenDensity(ctx) * dp + 0.5f).toInt())
    }

    fun convertDpToPx(ctx: Context, dp: Float): Float {
        return getScreenDensity(ctx) * dp
    }

}