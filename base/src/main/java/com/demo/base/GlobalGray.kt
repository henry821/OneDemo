package com.demo.base

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.Log
import android.view.View
import com.demo.base.datastructure.ObservableArrayList

/**
 * Description 视图灰度化
 * Author henry
 * Date   2023/1/8
 */
class GlobalGray {

    companion object {
        @JvmStatic
        fun enable() {
            try {
                //灰色paint
                val matrix = ColorMatrix()
                matrix.setSaturation(0f)
                val paint = Paint()
                paint.colorFilter = ColorMatrixColorFilter(matrix)

                //反射获取WindowManagerGlobal
                val windowManagerGlobal = Class.forName("android.view.WindowManagerGlobal")
                val getInstanceMethod = windowManagerGlobal.getDeclaredMethod("getInstance")
                getInstanceMethod.isAccessible = true
                val windowManagerGlobalInstance = getInstanceMethod.invoke(windowManagerGlobal)

                //反射获取mViews
                val viewsField = windowManagerGlobal.getDeclaredField("mViews")
                viewsField.isAccessible = true
                val viewsObject = viewsField.get(windowManagerGlobalInstance) as ArrayList<View>

                //创建具有数据感知能力的ObservableArrayList
                val observableArrayList = ObservableArrayList<View>()
                observableArrayList.addOnListChangedListener(object :
                    ObservableArrayList.LoggableListener<View>() {
                    override fun onAdd(list: ArrayList<View>?, start: Int, count: Int) {
                        super.onAdd(list, start, count)
                        val view = list?.get(start)
                        view?.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
                        view?.visibility = View.GONE
                    }
                })

                //将原有数据添加到新创建的list
                observableArrayList.addAll(viewsObject)
                //替换掉原有mViews
                viewsField.set(windowManagerGlobalInstance, observableArrayList)
            } catch (e: Exception) {
                Log.e("GlobalGray", "enable: ", e)
            }
        }
    }

}