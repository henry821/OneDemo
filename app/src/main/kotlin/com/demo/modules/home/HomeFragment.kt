package com.demo.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.one.R
import com.demo.one.databinding.HomeTitleItemBinding

class HomeFragment : Fragment(R.layout.home_fragment) {

    private val titleAdapter = TitleAdapter { findNavController().navigate(it.navAction) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view.findViewById<RecyclerView>(R.id.recyclerView)) {
            adapter = titleAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
        titleAdapter.submitList(
            listOf(
                TitleItem(getString(R.string.lottie), R.id.action_home_to_lottie),
                TitleItem(getString(R.string.gesture), R.id.action_home_to_gesture),
                TitleItem(getString(R.string.touch_event), R.id.action_home_to_touch_event),
                TitleItem(
                    getString(R.string.scale_type),
                    R.id.action_home_to_scale_type
                ),
                TitleItem(getString(R.string.animator_app_name), R.id.action_home_to_animator),
                TitleItem(getString(R.string.edit_text), R.id.action_home_to_edit_text),
                TitleItem(getString(R.string.fresco), R.id.action_home_to_fresco),
                TitleItem(getString(R.string.paging), R.id.action_home_to_paging),
                TitleItem(getString(R.string.gallery), R.id.action_home_to_gallery),
            )
        )
    }

    class TitleAdapter(private val clickItemAction: (TitleItem) -> Unit) :
        ListAdapter<TitleItem, MainViewHolder>(object : DiffUtil.ItemCallback<TitleItem>() {
            override fun areItemsTheSame(oldItem: TitleItem, newItem: TitleItem) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: TitleItem, newItem: TitleItem) =
                oldItem == newItem
        }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = HomeTitleItemBinding.inflate(inflater, parent, false)
            return MainViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val item = getItem(position)
            holder.itemView.setOnClickListener { clickItemAction.invoke(item) }
            holder.bind(item.title)
        }
    }

    class MainViewHolder(private val binding: HomeTitleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: String) {
            binding.title.text = data
        }
    }

}

data class TitleItem(val title: String, val navAction: Int)