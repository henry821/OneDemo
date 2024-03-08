package com.demo.modules.recyclerview

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.demo.one.R
import com.demo.utils.dp
import com.demo.utils.showToast

/**
 * 左滑展示删除按钮，点击删除
 */
@SuppressLint("ClickableViewAccessibility")
class SwipeToDeleteCallback(host: RecyclerView, clickDeleteAction: (Int) -> Unit) :
    SimpleCallback(0, ItemTouchHelper.START) {

    private val deleteBitmap =
        BitmapFactory.decodeResource(host.resources, R.drawable.ic_delete)
    private val deleteTouchSize = 72f.dp
    private val deleteDrawSize = 40f.dp
    private val margin = (deleteTouchSize - deleteDrawSize) / 2

    /**
     * 记录删除按钮展示区域，<itemView的Position,删除按钮展示区域>
     */
    private val deleteTouchAreaMap = mutableMapOf<Int, Rect>()

    private val detector =
        GestureDetector(host.context, DeleteGestureListener(deleteTouchAreaMap, clickDeleteAction))

    init {
        host.setOnTouchListener { _, event -> detector.onTouchEvent(event) }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val directionStr = if (direction == ItemTouchHelper.START) "Start" else "End"
        val itemView = viewHolder.itemView
        if (itemView.translationX.toInt() != 0) {
            val rec = Rect().also { itemView.getHitRect(it) }
            deleteTouchAreaMap[viewHolder.layoutPosition] =
                Rect(rec.right, rec.top, itemView.right, rec.bottom)
            showToast(viewHolder.itemView.context, "[onSwipe] direction: $directionStr")
        }
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = 0.8f

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean,
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            val percent = deleteTouchSize / itemView.width
            itemView.translationX = dX * percent
            val dst = iconRecF(itemView)
            c.drawBitmap(deleteBitmap, null, dst, null)
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder.itemView.translationX.toInt() == 0) {
            deleteTouchAreaMap.remove(viewHolder.layoutPosition)
        }
    }

    /**
     * 滑动时，删除按钮由0-100%缩放
     */
    private fun iconRecF(itemView: View): RectF {
        val left = itemView.right.toFloat() + itemView.translationX + margin
        val right = itemView.right.toFloat() - margin
        // height = width = right - left
        val verticalMargin = (itemView.height - (right - left)) / 2
        return RectF(
            left,
            itemView.top.toFloat() + verticalMargin,
            right,
            itemView.bottom.toFloat() - verticalMargin
        )
    }

    private class DeleteGestureListener(
        private val deleteTouchMapRef: Map<Int, Rect>,
        val clickDeleteAction: (Int) -> Unit,
    ) : SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            deleteTouchMapRef.firstNotNullOfOrNull {
                if (it.value.contains(e.x.toInt(), e.y.toInt())) it.key else null
            }?.also { clickDeleteAction.invoke(it) } ?: return super.onSingleTapUp(e)
            return true
        }
    }
}