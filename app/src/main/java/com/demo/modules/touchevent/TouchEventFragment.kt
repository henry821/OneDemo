package com.demo.modules.touchevent

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.demo.one.databinding.FragmentTouchEventBinding

/**
 * Description 触摸事件分发
 * Author henry
 * Date   2022/12/26
 */
class TouchEventFragment : Fragment() {

    private var _binding: FragmentTouchEventBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTouchEventBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

class TouchParentViewGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "--------------")
        Log.i(
            TAG,
            "dispatchTouchEvent[开始]: ${MotionEvent.actionToString(ev?.action ?: -1)}"
        )
        val result = super.dispatchTouchEvent(ev)
        Log.e(
            TAG,
            "dispatchTouchEvent[结束]: ${MotionEvent.actionToString(ev?.action ?: -1)}, result: $result"
        )
        return result
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.i(
            TAG,
            "onInterceptTouchEvent[开始]: ${MotionEvent.actionToString(ev?.action ?: -1)}"
        )
        val result = ev?.action == MotionEvent.ACTION_MOVE
        Log.e(
            TAG,
            "onInterceptTouchEvent[结束]: ${MotionEvent.actionToString(ev?.action ?: -1)}, result: $result"
        )
        return result
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i(
            TAG,
            "onTouchEvent[开始]: ${MotionEvent.actionToString(event?.action ?: -1)}"
        )
        val result = true
        Log.e(
            TAG,
            "onTouchEvent[结束]: ${MotionEvent.actionToString(event?.action ?: -1)}, result: $result"
        )
        return result
    }

    companion object {
        private const val TAG = "TouchParentViewGroup"
    }

}

class TouchChildView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.i(
            TAG,
            "dispatchTouchEvent[开始]: ${MotionEvent.actionToString(ev?.action ?: -1)}"
        )
        val result = super.dispatchTouchEvent(ev)
        Log.e(
            TAG,
            "dispatchTouchEvent[结束]: ${MotionEvent.actionToString(ev?.action ?: -1)}, result: $result"
        )
        return result
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i(
            TAG,
            "onTouchEvent[开始]: ${MotionEvent.actionToString(event?.action ?: -1)}"
        )
        val result = true
        Log.e(
            TAG,
            "onTouchEvent[结束]: ${MotionEvent.actionToString(event?.action ?: -1)}, result: $result"
        )
        return result
    }

    companion object {
        private const val TAG = "TouchChildView"
    }
}