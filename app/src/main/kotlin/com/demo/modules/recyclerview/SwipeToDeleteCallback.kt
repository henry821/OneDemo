package com.demo.modules.recyclerview

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.demo.utils.showToast

/**
 * 左滑展示删除按钮，点击删除
 */
@SuppressLint("ClickableViewAccessibility")
class SwipeToDeleteCallback(host: RecyclerView, private val clickDeleteAction: (Int) -> Unit) :
    SimpleCallback(0, ItemTouchHelper.START) {
    private val deleteBitmap =
        BitmapFactory.decodeResource(Resources.getSystem(), android.R.drawable.ic_delete)
    private val paint = Paint().also { it.color = "#D32F2F".toColorInt() }

    /**
     * 记录删除按钮展示区域，<itemView的Position,删除按钮展示区域>
     */
    private val deleteTouchAreaMap = mutableMapOf<Int, Rect>()

    /**
     * 删除操作的二次校验，原因：
     * - [deleteTouchAreaMap]的更新依赖于[onSwiped]回调，但是有时itemView收起时没有触发[onSwiped]
     * - 所以在真正触发[clickDeleteAction]前判断itemView是否有平移，如果没有的话说明itemView是收起状态，则不触发删除操作
     */
    private val doubleCheck: (Int) -> Unit = {
        if (host.layoutManager?.findViewByPosition(it)?.translationX != 0f) {
            clickDeleteAction.invoke(it)
        }
    }

    private val detector =
        GestureDetector(host.context, DeleteGestureListener(deleteTouchAreaMap, doubleCheck))

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
        val rec = Rect().also { itemView.getHitRect(it) }
        deleteTouchAreaMap[viewHolder.layoutPosition] =
            Rect(rec.right, rec.top, itemView.right, rec.bottom)
        showToast(viewHolder.itemView.context, "[onSwipe] direction: $directionStr")
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = 0.8f

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean,
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            val height = itemView.height.toFloat()
            val width = height / 5
            viewHolder.itemView.translationX = dX / 5
            val background = RectF(
                itemView.right.toFloat() + dX / 5,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            )
            c.drawRect(background, paint)
            val dst = RectF(
                (itemView.right + dX / 7),
                itemView.top.toFloat() + width,
                itemView.right.toFloat() + dX / 20,
                itemView.bottom.toFloat() - width
            )
            c.drawBitmap(deleteBitmap, null, dst, paint)
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
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