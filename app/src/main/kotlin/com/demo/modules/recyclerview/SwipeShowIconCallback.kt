package com.demo.modules.recyclerview

import android.R
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.demo.utils.showToast

/**
 * 左滑展示删除按钮
 */
class SwipeShowIconCallback : SimpleCallback(0, ItemTouchHelper.START) {

    private val deleteBitmap =
        BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_delete)
    private val paint = Paint().also { it.color = "#D32F2F".toColorInt() }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val directionStr = if (direction == ItemTouchHelper.START) "Start" else "End"
        showToast(viewHolder.itemView.context, "[onSwipe] direction: $directionStr")
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = 0.8f

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        Log.i("whw", "onChildDraw: $actionState -- $isCurrentlyActive -- $dX")
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
}