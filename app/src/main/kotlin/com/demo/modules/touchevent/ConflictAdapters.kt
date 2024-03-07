package com.demo.modules.touchevent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.one.R

class ConflictParentAdapter : RecyclerView.Adapter<ConflictParentAdapter.ConflictParentViewHolder>() {

    var dataList: MutableList<ConflictParentData> = mutableListOf()
        set(value) {
            dataList.clear()
            dataList.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConflictParentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_touch_conflict_parent_item, parent, false)
        return ConflictParentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConflictParentViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ConflictParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val index = itemView.findViewById<TextView>(R.id.index)
        private val childRv = itemView.findViewById<RecyclerView>(R.id.child_rv)
        private val childAdapter = ConflictChildAdapter()

        init {
            with(childRv) {
                adapter = childAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            }
        }

        fun bindData(data: ConflictParentData) {
            index.text = "父布局: ${data.position}"
            childAdapter.dataList = data.list.list
        }
    }

}

class ConflictChildAdapter : RecyclerView.Adapter<ConflictChildAdapter.ConflictChildViewHolder>() {

    var dataList: List<Int> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConflictChildViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_touch_conflict_child_item, parent, false)
        return ConflictChildViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConflictChildViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ConflictChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val index = itemView.findViewById<TextView>(R.id.index)

        fun bindData(data: Int) {
            index.text = "子布局: $data"
        }
    }

}

data class ConflictParentData(val position: Int, val list: ConflictChildData)
data class ConflictChildData(val list: List<Int>)