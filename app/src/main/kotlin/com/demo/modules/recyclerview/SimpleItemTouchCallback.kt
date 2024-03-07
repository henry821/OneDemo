package com.demo.modules.recyclerview

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
}