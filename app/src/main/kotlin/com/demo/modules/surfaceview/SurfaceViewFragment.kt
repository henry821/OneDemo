package com.demo.modules.surfaceview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.demo.one.databinding.FragmentSurfaceviewBinding
import com.demo.utils.dp

/**
 * Description
 * Author henry
 * Date   2023/3/15
 */
class SurfaceViewFragment : Fragment() {

    private var _binding: FragmentSurfaceviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurfaceviewBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resize.setOnClickListener {
            binding.mySurfaceView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                height = 300.dp
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}