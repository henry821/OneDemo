package com.demo.utils

import android.util.Log

/**
 * Description
 * Author henry
 * Date   2023/1/6
 */
private const val TAG = "Lifecycle"

fun Any.lifecycleLogV(text: String) {
    Log.v(TAG, "[${this.javaClass.simpleName}] $text")
}

fun Any.lifecycleLogD(text: String) {
    Log.d(TAG, "[${this.javaClass.simpleName}] $text")
}

fun Any.lifecycleLogI(text: String) {
    Log.i(TAG, "[${this.javaClass.simpleName}] $text")
}

fun Any.lifecycleLogE(text: String) {
    Log.e(TAG, "[${this.javaClass.simpleName}] $text")
}

fun Any.lifecycleLogW(text: String) {
    Log.w(TAG, "[${this.javaClass.simpleName}] $text")
}

fun Any.lifecycleLogAttach(text: String = "") {
    lifecycleLogD("onAttach $text")
}

fun Any.lifecycleLogCreate(text: String = "") {
    lifecycleLogD("onCreate $text")
}

fun Any.lifecycleLogCreateView(text: String = "") {
    lifecycleLogD("onCreateView $text")
}

fun Any.lifecycleLogViewCreated(text: String = "") {
    lifecycleLogD("onViewCreated $text")
}

fun Any.lifecycleLogActivityCreated(text: String = "") {
    lifecycleLogD("onActivityCreated $text")
}

fun Any.lifecycleLogStart(text: String = "") {
    lifecycleLogD("onStart $text")
}

fun Any.lifecycleLogResume(text: String = "") {
    lifecycleLogD("onResume $text")
}

fun Any.lifecycleLogPause(text: String = "") {
    lifecycleLogI("onPause $text")
}

fun Any.lifecycleLogStop(text: String = "") {
    lifecycleLogI("onStop $text")
}

fun Any.lifecycleLogSaveInstanceState(text: String = "") {
    lifecycleLogI("SaveInstanceState $text")
}

fun Any.lifecycleLogDestroyView(text: String = "") {
    lifecycleLogE("onDestroyView $text")
}

fun Any.lifecycleLogDestroy(text: String = "") {
    lifecycleLogE("onDestroy $text")
}

fun Any.lifecycleLogDetach(text: String = "") {
    lifecycleLogE("onDetach $text")
}