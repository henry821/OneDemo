package com.demo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.demo.one.databinding.FragmentCoroutineBinding
import com.demo.utils.appendThreadInfo
import kotlinx.coroutines.*

/**
 * 协程
 * Created by hengwei on 2022/8/8.
 */
class CoroutineFragment : Fragment() {

    private var _binding: FragmentCoroutineBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            Log.i(TAG, "onCreate: 开始执行".appendThreadInfo)
            repeatPrinting("1")
            repeatPrinting("2")
            repeatPrinting("3")
            launch { repeatPrinting("4") }
            launch { repeatPrinting("5") }
            launch { repeatPrinting("6") }
            Log.i(TAG, "onCreate: 执行结束".appendThreadInfo)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCoroutineBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.btnCancel?.setOnClickListener { lifecycleScope.cancel() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private suspend fun repeatPrinting(prefix: String) {
        withContext(Dispatchers.IO) {
            repeat(5) {
                delay(300)
                Log.d(TAG, "repeatPrinting: ${prefix}_$it".appendThreadInfo)
            }
        }
    }

    companion object {
        private const val TAG = "CoroutineFragment"
    }

}