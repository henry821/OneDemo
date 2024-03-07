package com.demo.modules.recyclerview

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.demo.utils.showToast

class SimpleItemTouchCallback(
    dragDirs: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    swipeDirs: Int = ItemTouchHelper.START or ItemTouchHelper.END,
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        showToast(
            viewHolder.itemView.context,
            "[onMove] from: ${viewHolder.layoutPosition} to: ${target.layoutPosition}"
        )
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val directionStr = if (direction == ItemTouchHelper.START) "Start" else "End"
        showToast(viewHolder.itemView.context, "[onSwipe] direction: $directionStr")
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        val itemView = viewHolder.itemView
        Log.i(
            "whw",
            """onChildDraw: 
                |left=${itemView.left}, right=${itemView.right}, 
                |dx=${dX}, dy=${dY}
                |translationX=${itemView.translationX}, translationY=${itemView.translationY}
                |""".trimMargin()
        )
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}