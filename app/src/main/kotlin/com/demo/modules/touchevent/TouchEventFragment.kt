package com.demo.modules.touchevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.one.databinding.TouchEventFragmentBinding

/**
 * Description 触摸事件分发
 * Author henry
 * Date   2022/12/26
 */
class TouchEventFragment : Fragment() {

    private var _binding: TouchEventFragmentBinding? = null
    private val binding: TouchEventFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = TouchEventFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val conflictData = mutableListOf<ConflictParentData>()
        repeat(8) { index ->
            val childList = (0..20).toList()
            val data = ConflictParentData(index, ConflictChildData(childList))
            conflictData.add(data)
        }
        val parentAdapter = ConflictParentAdapter()
        with(binding.verticalRv) {
            adapter = parentAdapter
            layoutManager = LinearLayoutManager(context)
        }
        parentAdapter.dataList = conflictData
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}