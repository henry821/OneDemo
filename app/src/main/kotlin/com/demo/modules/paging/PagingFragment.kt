package com.demo.modules.paging

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.one.R
import com.demo.one.databinding.PagingFragmentBinding
import com.demo.utils.launchAndRepeatWithViewLifecycle
import kotlinx.coroutines.flow.collectLatest

class PagingFragment : Fragment(R.layout.paging_fragment) {

    private val userVM by viewModels<UserViewModel>()
    private lateinit var binding: PagingFragmentBinding
    private val userAdapter = UserAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PagingFragmentBinding.bind(view)
        with(binding.userList) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        launchAndRepeatWithViewLifecycle {
            userVM.flow.collectLatest {
                userAdapter.submitData(it)
            }
        }
    }

}