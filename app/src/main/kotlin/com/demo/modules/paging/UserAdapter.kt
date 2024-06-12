package com.demo.modules.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.modules.paging.mock.User
import com.demo.one.R
import com.demo.one.databinding.PagingUserRecyclerItemBinding

class UserAdapter : PagingDataAdapter<User, UserViewHolder>(User.diffCallback) {
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PagingUserRecyclerItemBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }
}

class UserViewHolder(private val binding: PagingUserRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: User?) {
        binding.avatar.setImageResource(R.drawable.ic_account_circle)
        binding.name.text = data?.name
    }
}