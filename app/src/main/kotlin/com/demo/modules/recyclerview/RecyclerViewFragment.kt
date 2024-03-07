package com.demo.modules.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.one.R
import com.demo.one.databinding.RvFragmentBinding
import com.demo.one.databinding.RvRecycleItemUserBinding
import com.demo.utils.launchAndRepeatWithViewLifecycle

class RecyclerViewFragment : Fragment() {

    private lateinit var binding: RvFragmentBinding
    private val userAdapter = UserAdapter(object : ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem == newItem
        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    })

    private val dataVM by viewModels<RecyclerViewViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = RvFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.rv) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            ItemTouchHelper(SwipeToDeleteCallback(this) {
                dataVM.remove(it)
            }).also { it.attachToRecyclerView(this) }
        }

        launchAndRepeatWithViewLifecycle {
            dataVM.userList.collect { userAdapter.submitList(it) }
        }

    }

    class UserAdapter(itemCallback: ItemCallback<User>) :
        ListAdapter<User, UserViewHolder>(itemCallback) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RvRecycleItemUserBinding.inflate(inflater, parent, false)
            return UserViewHolder(binding)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
            holder.bind(getItem(position))
    }

    class UserViewHolder(private val binding: RvRecycleItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            binding.avatar.setImageResource(R.drawable.ic_account_circle)
            binding.name.text = data.name
        }
    }
}